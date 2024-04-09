from django.contrib import admin
from .models import Player, ChessGame, ChessMove
# Register your models here.

class PlayerAdmin(admin.ModelAdmin):
    list_display = ('id', 'username', 'rating')
    search_fields = ('username',)
    list_filter = ('rating',)
    ordering = ('-rating',)


class ChessGameAdmin(admin.ModelAdmin):
    list_display = ('id', 'whitePlayer', 'blackPlayer')
    list_filter = ('whitePlayer', 'blackPlayer')


class ChessMoveAdmin(admin.ModelAdmin):
    list_display = ('game', 'player', 'move_from', 'move_to', 'promotion')
    list_filter = ('game', 'player')


admin.site.register(ChessGame, ChessGameAdmin)
admin.site.register(ChessMove, ChessMoveAdmin)
admin.site.register(Player, PlayerAdmin)
