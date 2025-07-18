from django.contrib import admin
from .models import Player, ChessGame, ChessMove

# Modelo de administración para gestionar jugadores.
class PlayerAdmin(admin.ModelAdmin):
    list_display = ('id', 'username', 'rating')
    search_fields = ('username',)
    list_filter = ('rating',)
    ordering = ('-rating',)

# Modelo de administración para gestionar partidas de ajedrez.
class ChessGameAdmin(admin.ModelAdmin):
    list_display = ('id', 'whitePlayer', 'blackPlayer')
    list_filter = ('whitePlayer', 'blackPlayer')

# Modelo de administración para gestionar movimientos de ajedrez.
class ChessMoveAdmin(admin.ModelAdmin):
    list_display = ('game', 'player', 'move_from', 'move_to', 'promotion')
    list_filter = ('game', 'player')

# Registra los modelos ChessGame, ChessMove y Player en el panel de administración de Django.
admin.site.register(ChessGame, ChessGameAdmin)
admin.site.register(ChessMove, ChessMoveAdmin)
admin.site.register(Player, PlayerAdmin)
