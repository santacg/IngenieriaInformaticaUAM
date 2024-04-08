from djoser.views import TokenCreateView
from djoser.conf import settings
from rest_framework import mixins, viewsets, status
from rest_framework.response import Response
from .models import ChessGame
from .serializers import ChessGameSerializer
from django.db.models import Q
import random
import pdb

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
        game = ChessGame.objects.filter(Q(whitePlayer=None) | Q(blackPlayer=None)).first()
        if game:
            return self.update(request, game, *args, **kwargs)  
        
        data = {'status': 'PENDING'}
        if random.choice([True, False]):
            data['whitePlayer'] = self.request.user.id
        else:
            data['blackPlayer'] = self.request.user.id

        request._full_data = data 
        return super().create(request, *args, **kwargs)


    def update(self, request, game, *args, **kwargs):
        if game.status != 'pending':
            return Response({'detail': 'Game is not pending'}, status=status.HTTP_400_BAD_REQUEST)

        update_data = {'status': 'ACTIVE'}
        if game.whitePlayer is None:
            update_data['whitePlayer'] = self.request.user.id
        else:
            update_data['blackPlayer'] = self.request.user.id

        request._full_data = update_data
        self.kwargs['pk'] = str(game.id)
        return super().update(request, *args, **kwargs)
