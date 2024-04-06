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
        # Intenta encontrar un juego pendiente que el usuario actual pueda unirse
        available_game = ChessGame.objects.filter(status='pending').exclude(whitePlayer=request.user).exclude(blackPlayer=request.user).first()

        if available_game:
            # Asigna el usuario actual como el segundo jugador y cambia el estado a ACTIVE
            if not available_game.whitePlayer:
                available_game.whitePlayer = request.user
            else:
                available_game.blackPlayer = request.user
            available_game.status = 'ChessGame.ACTIVE'
            available_game.save()
            serializer = self.get_serializer(available_game)
            return Response(serializer.data, status=status.HTTP_200_OK)
        else:
            # No hay juego disponible para unirse, crea un nuevo juego
            # Decide aleatoriamente si el usuario actual será el jugador blanco o negro
            player_role = random.choice(['whitePlayer', 'blackPlayer'])
            data = {player_role: request.user.id, 'status': ChessGame.PENDING}
            serializer = self.get_serializer(data=data)
            serializer.is_valid(raise_exception=True)
            self.perform_create(serializer)
            headers = self.get_success_headers(serializer.data)
            return Response(serializer.data, status=status.HTTP_201_CREATED, headers=headers)
    def update(self, request, *args, **kwargs):
        instance = self.get_object()
        partial = kwargs.pop('partial', False)
        serializer = self.get_serializer(instance, data=request.data, partial=partial)
        serializer.is_valid(raise_exception=True)

        if instance.status == 'active':
            return Response({'detail': 'Game is already active'}, status=status.HTTP_400_BAD_REQUEST)
        if instance.status == 'PENDING':
            instance.status = 'ACTIVE'
            # Asegúrate de asignar el jugador contrario si es necesario
            if not instance.whitePlayer:
                instance.whitePlayer = request.user
            elif not instance.blackPlayer:
                instance.blackPlayer = request.user
            instance.save()

        self.perform_update(serializer)

        return Response(serializer.data)
