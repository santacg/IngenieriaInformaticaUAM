import json
from django.test.testcases import async_to_sync
from djoser.conf import settings
from channels.db import database_sync_to_async
from django.contrib.auth.models import User
from .serializers import ChessMoveSerializer 
from .models import ChessMove, ChessGame, Player
from channels.generic.websocket import AsyncWebsocketConsumer
from rest_framework.authtoken.models import Token
from asgiref.sync import sync_to_async
import chess
import pdb

class ChessConsumer(AsyncWebsocketConsumer):
    async def connect(self):
        self.gameID = self.scope['url_route']['kwargs']['gameID']
        game = None
        game = await (sync_to_async(ChessGame.objects.filter)(id=self.gameID))        
        if game is None: 
          await self.send(text_data=json.dumps({
                'type': 'error',
                'message': 'Invalid token',
                'status': 'pending',
                'playerID': None,
            }))
          await self.close()
          return
        token = self.scope['query_string'].decode()       
 
        self.user = await self.get_user_by_token(token)
        if self.user is None:
            await self.send(text_data=json.dumps({
                'type': 'error',
                'message': 'Invalid token',
                'status': 'pending',
                'playerID': None,
            }))
            await self.close()
        else:
            await self.accept()
            await self.channel_layer.group_add(str(self.gameID), self.channel_name)
            await self.game_cb('game', 'OK', 'active', self.user.id)


    async def receive(self, text_data):
        data = json.loads(text_data)
        message = data['message']

        game = ChessGame.objects.get(id=self.gameID)
        if message == 'move':
            #verifica si el movimiento es válido
            try:
                # crea un nuevo movimiento
                move = ChessMove.objects.create(
                    game=game,
                    player=self.user,
                    move_from=data['from'],
                    move_to=data['to'],
                    promotion=data['promotion']
                )
                serializer = ChessMoveSerializer(move)
                serializer.is_valid(raise_exception=True)
                move.save()
                # envia el movimiento al otro jugador
                await self.channel_layer.group_send(self.gameID, {
                    'type':'move',
                    'from': data['from'],
                    'to': data['to'],
                    'playerID': data['playerID'],
                    'promotion': data['promotion'],
                })
                board = chess.Board(game.board_state)
                # si es jaque mate, ahogado, insuficiente material, 75 movimientos o 5 repeticiones
                if board.is_checkmate() or board.is_stalemate() or board.is_insufficient_material() or board.is_seventyfive_moves() or board.is_fivefold_repetition():
                    game.status = 'FINISHED'
                    game.winner = self.user
                    game.save()
            except Exception:
                await self.send(text_data=json.dumps({'error': 'Invalid move'}))
                return
                        

    async def game_cb(self, _type, message, status, playerID):
        await self.channel_layer.group_send(
            str(self.gameID),
            {
                'type': 'game.message',  # Esto define el método handler que será llamado
                'message': {
                    'type': _type,
                    'message': message,
                    'status': status,
                    'playerID': playerID,
                }
            }
        )

    async def game_message(self, event):
        await self.send(text_data=json.dumps(event['message']))

    async def move_cb(self, _type, message, status, playerID):
        await self.channel_layer.group_send(
            str(self.gameID),
            {
                'type': 'game.message',  # Esto define el método handler que será llamado
                'message': {
                    'type': _type,
                    'message': message,
                    'status': status,
                    'playerID': playerID,
                }
            }
        )

    async def move_message(self, event):
        await self.send(text_data=json.dumps(event['message']))

    async def disconnect(self, close_code):
        await self.channel_layer.group_discard(str(self.gameID), self.channel_name)

    @database_sync_to_async
    def get_user_by_token(self, token_key):
        try:
            token = Token.objects.get(key=token_key)
            return token.user
        except Token.DoesNotExist:
            return None

    @database_sync_to_async
    def get_game_by_id(self, gameID):
        game = ChessGame.objects.get(id=gameID)
        return game
