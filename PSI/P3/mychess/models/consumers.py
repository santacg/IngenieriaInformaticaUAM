import json
from django.test.testcases import ValidationError
from channels.db import database_sync_to_async
from .models import ChessMove, ChessGame
from channels.generic.websocket import AsyncWebsocketConsumer
from rest_framework.authtoken.models import Token
from asgiref.sync import sync_to_async
import chess

# Consumidor de WebSockets para el juego de ajedrez.
# Autor: Eduardo Junoy Ortega
class ChessConsumer(AsyncWebsocketConsumer):
    async def connect(self):
        # Obtiene el id de la partida y el usuario a partir de la URL
        self.gameID = self.scope['url_route']['kwargs']['gameID']
        self.game = await self.get_game_by_id(self.gameID)
        # Comprueba que la partida exista
        if self.game is None:
            await self.accept()
            await self.send(text_data=json.dumps({
                'type': 'error',
                'message': f'Invalid game with id {self.gameID}',
                'status': None,
                'playerID': None,
            }))
            await self.close()
            return
        # Obtiene el usuario a partir del token
        token = self.scope['query_string'].decode()
        self.user = await self.get_user_by_token(token)
        # Comprueba que el usuario esté en la partida
        if self.user is None or not await self.is_user_in_game(self.user, self.game):
            await self.accept()
            if self.user is None:
                message = 'Invalid token. Connection not authorized.'
            else:
                message = f'Invalid game with id {self.gameID}'
            await self.send(text_data=json.dumps({
                'type': 'error',
                'message': message,
                'status': self.game.status.upper(),
                'playerID': None,
            }))
            await self.close()
        else:
            await self.accept()
            await self.channel_layer.group_add(str(self.gameID), self.channel_name)
            await self.game_cb('game', 'OK', self.game.status.upper(), self.user.id)
    # Recibe un mensaje del jugador
    async def receive(self, text_data):
        data = json.loads(text_data)
        _from = ''
        to = ''
        playerID = ''
        promotion = ''
        # Comprueba el tipo de mensaje
        if data['type'] == 'move':
            try:
                _from = data['from']
                to = data['to']
                playerID = data['playerID']
                promotion = None
                promotion = data['promotion']
        # Crea un nuevo movimiento, se llama a save con la creación y en ese método se comprueba la validez del movimiento.
                await sync_to_async(ChessMove.objects.create)(
                    game=self.game,
                    player=self.user,
                    move_from=_from,
                    move_to=to,
                    promotion=promotion
                )
        # Envía el movimiento al otro jugador.
                await self.move_cb('move', _from, to, playerID, promotion, None)
                board = chess.Board(self.game.board_state)
        # Si es jaque mate, ahogado, insuficiente material, 75 movimientos o 5 repeticiones.
                if board.is_checkmate() or board.is_stalemate() or board.is_insufficient_material() or board.is_seventyfive_moves() or board.is_fivefold_repetition():
                    self.game.status = 'FINISHED'
                    self.game.winner = self.user
                    self.game.save()
            except ValidationError:
                message = f"Error: invalid move (game is not active)"
                await self.move_cb('error', _from, to, playerID, promotion, message)
            except ValueError:
                message = f'Error: invalid move {_from}{to}'
                await self.move_cb('error', _from, to, playerID, promotion, message)
            except Exception:
                await self.move_cb('error', _from, to, playerID, promotion, None)
        else:
            return
    # Envía un mensaje a todos los jugadores
    async def game_cb(self, _type, message, status, playerID):
        await self.channel_layer.group_send(
            str(self.gameID),
            {
                'type': 'game.message',  # Dfine el método handler que será llamado.
                'message': {
                    'type': _type,
                    'message': message,
                    'status': status,
                    'playerID': playerID,
                }
            }
        )
    # Envía un mensaje a todos los jugadores de un juego
    async def game_message(self, event):
        await self.send(text_data=json.dumps(event['message']))
    # Envía un movimiento a todos los jugadores
    async def move_cb(self, _type, _from, to, playerID, promotion, _message):
        await self.channel_layer.group_send(
            str(self.gameID),
            {
                'type': 'move.message',  # Define el método handler que será llamado.
                'message': {
                    'type': _type,
                    'from': _from,
                    'to': to,
                    'playerID': playerID,
                    'promotion': promotion,
                    'message': _message,
                }
            }
        )
    # Envía un mensaje a todos los jugadores
    async def move_message(self, event):
        await self.send(text_data=json.dumps(event['message']))
    # Desconecta al jugador
    async def disconnect(self, close_code):
        await self.channel_layer.group_discard(str(self.gameID), self.channel_name)
    # Obtiene el usuario a partir de un token
    @database_sync_to_async
    def get_user_by_token(self, token_key):
        try:
            token = Token.objects.get(key=token_key)
            return token.user
        except Token.DoesNotExist:
            return None
    
    # Obtiene una partida a partir de su id
    @database_sync_to_async
    def get_game_by_id(self, gameID):
        try:
            game = ChessGame.objects.get(id=gameID)
            return game
        except ChessGame.DoesNotExist:
            return None
    # Comprueba si un usuario está en una partida
    @database_sync_to_async
    def is_user_in_game(self, user, game):
        return game.whitePlayer == user or game.blackPlayer == user
