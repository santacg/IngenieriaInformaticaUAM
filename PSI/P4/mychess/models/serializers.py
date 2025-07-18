from rest_framework import serializers
from .models import ChessGame, ChessMove

# Serializador para el modelo ChessGame
# Autor: Carlos García Santa
class ChessGameSerializer(serializers.ModelSerializer):
    class Meta:
        model = ChessGame
        fields = ['id', 'status', 'whitePlayer', 'blackPlayer']

# Serializador para el modelo ChessMove
# Autor: Carlos García Santa
class ChessMoveSerializer(serializers.ModelSerializer):
    class Meta:
        model = ChessMove
        fields = ['id', 'game', 'player', 'move_from', 'move_to', 'promotion']
