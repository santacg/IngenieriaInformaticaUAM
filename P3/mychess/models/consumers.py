import json
from .serializers import ChessMoveSerializer 
from .models import ChessMove, ChessGame, Player
from asgiref.sync import async_to_sync
from channels.generic.websocket import AsyncWebsocketConsumer
import chess
import pdb

class ChessConsumer(AsyncWebsocketConsumer):
    ''' 
    Se verifica el token proporcionado en la URL de la conexión para asegurarse de que el usuario esté autorizado.
    Esto es, se comprueba que el token existe y se registra a qué usuario pertenece. Si el token es inválido,
    se envía un mensaje de error y se cierra la conexión. Si es válido, el usuario es aceptado en el canal WebSocket 
    y se le añade a un grupo (channel_layer) cuyo nombre es igual al identificador del juego.
    '''
    async def connect(self):
        pdb.set_trace()
        self.gameID = self.scope['url_route']['kwargs']['gameID']
        self.token = self.scope['url_route']['kwargs']['token']
        # acceder al usuario a través del token

        if self.user is None:
            status = 401
            message = 'Invalid token'
            type = 'error'
            await self.close()
        else:
            await self.accept()
            status = 200
            message = 'Connected to game'
            type = 'game'
            await self.channel_layer.group_add(self.gameID, self.channel_name)
            await self.send(text_data=json.dumps({
                'type':type,
                'message':message,
                'status':status, 
                'playerID':self.user.id
                }))

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
                    'promotion': data['promotion']

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
                        

    async def disconnect(self, close_code):
        await self.channel_layer.group_discard(self.user, self.channel_name)
