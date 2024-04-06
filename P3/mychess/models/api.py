from djoser.views import TokenCreateView
from djoser.conf import settings
from rest_framework import mixins, viewsets, status
from rest_framework.response import Response
from .models import ChessGame
from .serializers import ChessGameSerializer
import random

class MyTokenCreateView(TokenCreateView):
    def _action(self, serializer):
        response = super()._action(serializer)
        tokenString = response.data['auth_token']
        tokenObject = settings.TOKEN_MODEL.objects.get(key=tokenString)
        response.data['user_id'] = tokenObject.user.id
        response.data['rating'] = tokenObject.user.rating
        return response

class ChessGameViewSet(mixins.CreateModelMixin, mixins.UpdateModelMixin, viewsets.GenericViewSet):
    queryset = ChessGame.objects.all()
    serializer_class = ChessGameSerializer

    def create(self, request, *args, **kwargs):
        available_games = ChessGame.objects.filter(status='pending').exclude(whitePlayer=request.user).exclude(blackPlayer=request.user)
        if available_games.exists():
            game = available_games.first()
            if not game.whitePlayer:
                game.whitePlayer = request.user
            else:
                game.blackPlayer = request.user
            game.status = 'active'
            game.save()
            return Response(self.get_serializer(game).data, status=status.HTTP_201_CREATED)
        else:
            game = ChessGame()
            if random.choice([True, False]):
                game.whitePlayer = request.user
            else:
                game.blackPlayer = request.user
            game.status = 'pending'
            game.save()  # Asegúrate de guardar el juego
            return Response(self.get_serializer(game).data, status=status.HTTP_201_CREATED)

    def update(self, request, *args, **kwargs):
        # Implementa lógica específica de actualización si es necesario
        return super().update(request, *args, **kwargs)
