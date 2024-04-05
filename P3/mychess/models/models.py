from django.db import models
from django.contrib.auth.models import AbstractUser
from django.conf import settings
from django.core.exceptions import ValidationError
import chess
# Create your models here.

class Player(AbstractUser):
    rating = models.IntegerField(default=1200)

class ChessGame(models.Model):
    STATUS_CHOICES = [
            ('PENDING', 'Pendiente'),
            ('ACTIVE', 'Activa'),
            ('FINISHED', 'Finalizada'),
    ]
    status = models.CharField(max_length=8, choices=STATUS_CHOICES, default='PENDING')
    board_state = models.TextField(help_text="Almacena la posición de las piezas en formato FEN")
    start_time = models.DateTimeField(auto_now_add=True)
    end_time = models.DateTimeField(null=True, blank=True, help_text="Tiempo de finalización de la partida")
    time_control = models.CharField(max_length=50, help_text="Control de tiempo para cada jugador")
    white_player = models.ForeignKey(settings.AUTH_USER_MODEL, related_name="games_as_white", on_delete=models.CASCADE)
    black_player = models.ForeignKey(settings.AUTH_USER_MODEL, related_name='games_as_black', on_delete=models.CASCADE)
    winner = models.ForeignKey(settings.AUTH_USER_MODEL, related_name='games_won', null=True, blank=True, on_delete=models.SET_NULL, help_text="El ganador de la partida. Puede ser nulo si el juego está pendiente o ha terminado en empate.")

    def __str__(self):
        if self.white_player is None:
            white_player = 'Unknown'
        else:
            white_player = str(self.white_player)

        if self.black_player is None:
            black_player = 'Unknown'
        else:
            black_player = str(self.black_player)

        game_id = self.id if self.id is not None else "Pending"
        return f"Game ID = {game_id}; Players: White: {white_player}, Black: {black_player}"



class ChessMove(models.Model):
    PROMOTION_CHOICES = [
        ('Q', 'Queen'),
        ('R', 'Rook'),
        ('N', 'Knight'),
        ('B', 'Bishop'),
    ]

    game = models.ForeignKey('ChessGame', related_name='moves', on_delete=models.CASCADE)
    player = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    move_from = models.CharField(max_length=2)  # Ejemplo: "e2"
    move_to = models.CharField(max_length=2)    # Ejemplo: "e4"
    promotion = models.CharField(max_length=1, choices=PROMOTION_CHOICES, blank=True, null=True)

    def save(self, *args, **kwargs):
        if self.game.status != 'ACTIVE':
            raise ValidationError("La partida no está activa.")

        board = chess.Board(self.game.board_state)

        try:
            move = board.parse_san(f"{self.move_from}{self.move_to}")
            if board.is_legal(move):
                board.push(move)
            else:
                raise ValidationError("Movimiento no válido.")
        except ValueError:
            raise ValidationError("Movimiento no válido o no se pudo parsear.")

        self.game.board_state = board.fen()
        self.game.save()

        super().save(*args, **kwargs)

    def __str__(self):
        move_description = f"{self.player} desde {self.move_from} a {self.move_to}"
        if self.promotion:
            promotion_piece = next((piece for code, piece in self.PROMOTION_CHOICES if code == self.promotion), None)
            move_description += f", promocionado a {promotion_piece}"
        return move_description
