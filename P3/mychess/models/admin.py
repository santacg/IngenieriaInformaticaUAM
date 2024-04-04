from django.contrib import admin
from .models import Player, ChessGame, ChessMove
# Register your models here.

admin.site.register(Player)

class ChessGameAdmin(admin.ModelAdmin):
    list_display = ('id', 'status', 'start_time', 'end_time', 'time_control', 'white_player', 'black_player', 'winner')
    list_filter = ('status', 'white_player', 'black_player', 'winner')
    search_fields = ('white_player__username', 'black_player__username', 'winner__username')

class ChessMoveAdmin(admin.ModelAdmin):
    list_display = ('game', 'player', 'move_from', 'move_to', 'promotion')
    list_filter = ('game', 'player')
    search_fields = ('player__username', 'move_from', 'move_to')

admin.site.register(ChessGame, ChessGameAdmin)
admin.site.register(ChessMove, ChessMoveAdmin)
