

import json
import api
from channels.generic.websocket import AsyncWebsocketConsumer

class GameConsumer(AsyncWebsocketConsumer):
    ''' 
    Se verifica el token proporcionado en la URL de la conexión para asegurarse de que el usuario esté autorizado.
    Esto es, se comprueba que el token existe y se registra a qué usuario pertenece. Si el token es inválido,
    se envía un mensaje de error y se cierra la conexión. Si es válido, el usuario es aceptado en el canal WebSocket 
    y se le añade a un grupo (channel_layer) cuyo nombre es igual al identificador del juego.
    '''
    async def connect(self):
        self.token = self.scope['url_route']['kwargs']['token']
        # acceder al usuario a través del token
        self.user = await api.MyTokenCreateView.as_view()
        
        if self.user is None:
            await self.close()
        else:
            await self.accept()
            await self.send(text_data=json.dumps({
                'type':'game'/'error',
                'message':message,
                'status':status, 
                'playerID':playerID
                }))
            await self.channel_layer.group_add(self.user, self.channel_name)

    async def receive(self, text_data):
        data = json.loads(text_data)
        message = data['message']

        if message == 'game':
            await self.game_cb(data)
        elif message == 'move':
            await self.move_cb(data)
        else:
            await self.send(text_data=json.dumps({'error': 'Invalid message'}))

    async def game_cb(self, event):
        game = event['game']
        await self.send(text_data=json.dumps({'game': game}))

    async def move_cb(self, event):
        move = event['move']
        await self.send(text_data=json.dumps({
            'type':'move'/'error',
            'from':_from,'to':to,
            'playerID':playerID,
            'promotion':promotion
            }))

    async def disconnect(self, close_code):
        await self.channel_layer.group_discard(self.user, self.channel_name)
