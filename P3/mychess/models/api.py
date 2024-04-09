from djoser.views import TokenCreateView
from djoser.conf import settings
from rest_framework import mixins, viewsets, status
from rest_framework.response import Response
from .models import ChessGame
from .serializers import ChessGameSerializer
from django.db.models import Q
import random

# Vista para gestionar tokens de autenticación.
class MyTokenCreateView(TokenCreateView):
    def _action(self, serializer):
        # Llama a la acción del padre y añade el id del usuario y su rating al token
        response = super()._action(serializer)
        tokenString = response.data['auth_token']
        tokenObject = settings.TOKEN_MODEL.objects.get(key=tokenString)
        response.data['user_id'] = tokenObject.user.id
        response.data['rating'] = tokenObject.user.rating
        return response

# Vista para gestionar partidas de ajedrez.
# Autor: Carlos García Santa
class ChessGameViewSet(mixins.CreateModelMixin, mixins.UpdateModelMixin, viewsets.GenericViewSet):
    queryset = ChessGame.objects.all()
    serializer_class = ChessGameSerializer

    # Crea una partida si no hay ninguna pendiente, o se une a una partida pendiente.
    def create(self, request, *args, **kwargs):
        # Busca una partida pendiente
        game = ChessGame.objects.filter(
            Q(whitePlayer=None) | Q(blackPlayer=None)).first()
        if game:
            return self.update(request, game, *args, **kwargs)
        # Crea una partida en estado pendiente
        data = {'status': 'PENDING'}
        if random.choice([True, False]):
            data['whitePlayer'] = self.request.user.id
        else:
            data['blackPlayer'] = self.request.user.id
        # Crea la partida
        request._full_data = data
        return super().create(request, *args, **kwargs)
    
    # Actualiza una partida pendiente para unirse a ella.
    def update(self, request, game, *args, **kwargs):
        # Comprueba que la partida esté pendiente
        if game.status != 'pending':
            return Response({'detail': 'Game is not pending'}, status=status.HTTP_400_BAD_REQUEST)
        # Actualiza la partida para unirse a ella
        update_data = {'status': 'ACTIVE'}
        if game.whitePlayer is None:
            update_data['whitePlayer'] = self.request.user.id
        else:
            update_data['blackPlayer'] = self.request.user.id
        # Actualiza la partida
        request._full_data = update_data
        self.kwargs['pk'] = str(game.id)
        return super().update(request, *args, **kwargs)
