from typing import Type
from django.db import models
from django.contrib.auth.models import AbstractUser
from django.conf import settings
from django.core.exceptions import ValidationError
import chess
# Create your models here.

class Player(AbstractUser):
    username = models.CharField(unique=True, max_length=50)
    rating = models.IntegerField(default=-1)

    def __str__(self):
        return f"{self.username} ({self.rating})" 

class ChessGame(models.Model):
    PENDING = 'PENDING'
    ACTIVE = 'ACTIVE'
    FINISHED = 'FINISHED'
    DEFAULT_BOARD_STATE = chess.STARTING_FEN
    STATUS_CHOICES = [
            ('PENDING', 'pending'),
            ('ACTIVE', 'active'),
            ('FINISHED', 'finished'),
    ]
    status = models.CharField(max_length=64, choices=STATUS_CHOICES, default='pending')
    board_state = models.TextField(default=DEFAULT_BOARD_STATE, help_text="Almacena la posición de las piezas en formato FEN")
    start_time = models.DateTimeField('Start', auto_now=True, null=True, blank=True, help_text="Game starting time")
    end_time = models.DateTimeField('Ending', null=True, blank=True, help_text="Game ending time")
    timeControl = models.CharField('Time Control', max_length=50, help_text="Control de tiempo para cada jugador")
    whitePlayer = models.ForeignKey(settings.AUTH_USER_MODEL, related_name='whitePlayer', on_delete=models.CASCADE, null=True, blank=True)
    blackPlayer = models.ForeignKey(settings.AUTH_USER_MODEL, related_name='blackPlayer', on_delete=models.CASCADE, null=True, blank=True)
    winner = models.ForeignKey(settings.AUTH_USER_MODEL, related_name='games_won', null=True, blank=True, on_delete=models.SET_NULL, help_text="El ganador de la partida. Puede ser nulo si el juego está pendiente o ha terminado en empate.")

    def __str__(self):
        white_data = "unknown" if self.whitePlayer is None else str(self.whitePlayer)
        black_data = "unknown" if self.blackPlayer is None else str(self.blackPlayer)

        return f"GameID=({self.id}) {white_data} vs {black_data}"


class ChessMove(models.Model):
    PROMOTION_CHOICES = [
        ('Q', 'Queen'),
        ('R', 'Rook'),
        ('N', 'Knight'),
        ('B', 'Bishop'),
    ]

    game = models.ForeignKey('ChessGame', related_name='game', on_delete=models.CASCADE)
    player = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    move_from = models.CharField(max_length=2) 
    move_to = models.CharField(max_length=2)   
    promotion = models.CharField(max_length=1, choices=PROMOTION_CHOICES, blank=True, null=True)

    def save(self, *args, **kwargs):
        if self.game.status != 'active':
            raise ValidationError("Game is not active")

        board = chess.Board(self.game.board_state)

        if self.promotion:
            move = chess.Move.from_uci(f"{self.move_from}{self.move_to}{self.promotion}")
        else:
            move = chess.Move.from_uci(f"{self.move_from}{self.move_to}")

        if move not in board.legal_moves:
            raise ValueError(-1)
                 
        board.push(move)
        self.game.board_state = board.fen()
        self.game.save()

        super().save(*args, **kwargs)

    def __str__(self):
        move_description = f"{self.player}: {self.move_from} -> {self.move_to}"
        if self.promotion:
            promotion_piece = next((piece for code, piece in self.PROMOTION_CHOICES if code == self.promotion), None)
            move_description += f", promoted to {promotion_piece}"
        return move_description
