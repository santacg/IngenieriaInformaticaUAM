from django.test import TestCase
from .models import Player, ChessGame, ChessMove

class TestP3(TestCase):
# Tests de models
    def test_030_en_passant(self):
        fen = "rnbqkbnr/pppp1ppp/8/3Pp3/8/8/PPP1PPPP/RNBQKBNR b KQkq e3 0 1"
        self.game.board_state = fen
        self.game.save()
        move = ChessMove(
            game=self.game,
            player=self.player2,
            move_from='d4',
            move_to='e3',
            promotion=''
        )
        move.save()
        updated_game = ChessGame.objects.get(id=self.game.id)
        self.assertEqual(
            updated_game.board_state,
            "rnbqkbnr/pppp1ppp/8/3p4/8/8/PPP1PPPP/RNBQKBNR w KQkq - 0 1"
        )
    
    def test_035_invalid_en_passant(self):
        fen = "rnbqkbnr/pppp1ppp/8/3Pp3/8/8/PPP1PPPP/RNBQKBNR b KQkq e3 0 1"
        self.game.board_state = fen
        self.game.save()
        move = ChessMove(
            game=self.game,
            player=self.player2,
            move_from='d4',
            move_to='e4',
            promotion=''
        )
        with self.assertRaises(ValueError):
            move.save()
        updated_game = ChessGame.objects.get(id=self.game.id)
        self.assertEqual(
            updated_game.board_state,
            fen
        )
    
    def test_040_invalid_promotion(self):
        fen = "rnbqkbnr/pppp1ppp/8/3Pp3/8/8/PPP1PPPP/RNBQKBNR b KQkq e3 0 1"
        self.game.board_state = fen
        self.game.save()
        move = ChessMove(
            game=self.game,
            player=self.player2,
            move_from='d4',
            move_to='e5',
            promotion='q'
        )
        with self.assertRaises(ValueError):
            move.save()
        updated_game = ChessGame.objects.get(id=self.game.id)
        self.assertEqual(
            updated_game.board_state,
            fen
        )
    
    def test_045_invalid_castle(self):
        fen = "r1bqk1nr/pppp1ppp/2n5/2b1p3/2B1P3/5N2/PPPP1PPP/RNBQK2R"\
              " w KQkq - 0 1"
        self.game.board_state = fen
        self.game.save()
        move = ChessMove(
            game=self.game,
            player=self.player1,
            move_from='e1',
            move_to='g1',
            promotion=''
        )
        with self.assertRaises(ValueError):
            move.save()
        updated_game = ChessGame.objects.get(id=self.game.id)
        self.assertEqual(
            updated_game.board_state,
            fen
        )
    
    def test_050_invalid_move_from(self):
        move = ChessMove(
            game=self.game,
            player=self.player1,
            move_from='z9',
            move_to='e4'
        )
        with self.assertRaises(ValueError):
            move.save()

    # Tests de consumers       
    async def test_023_en_passant(self):
        # en passant
        moves = [  # from to promoted_piece
            ["e2", "e4", ""],
            ["e7", "e5", ""],
            ["d2", "d4", ""],
            ["e5", "d4", ""],
            ["d1", "d4", ""],
            ["d7", "d5", ""],
            ["d4", "e5", ""],
            ["f7", "f5", ""],
            ["e5", "f6", ""],
            ]

        await self.play_a_few_moves(moves)

    async def test_024_invalid_en_passant(self):
        # invalid en passant
        moves = [  # from to promoted_piece
            ["e2", "e4", ""],
            ["e7", "e5", ""],
            ["d2", "d4", ""],
            ["e5", "d4", ""],
            ["d1", "d4", ""],
            ["d7", "d5", ""],
            ["d4", "e5", ""],
            ["f7", "f5", ""],
            ["e5", "e6", ""],
            ]

        await self.play_a_few_moves(moves)
    
    async def test_025_invalid_castle(self):
        # invalid castle
        moves = [  # from to promoted_piece
            ["e2", "e4", ""],
            ["e7", "e5", ""],
            ["f1", "c4", ""],
            ["f8", "c5", ""],
            ["g1", "f3", ""],
            ["g8", "f6", ""],
            ["e1", "g1", ""],
            ["d7", "d6", ""],
            ["f1", "e1", ""],
            ["g8", "f7", ""],
            ]

        await self.play_a_few_moves(moves)
    
    async def test_026_invalid_promotion(self):
        # invalid promotion
        moves = [  # from to promoted_piece
            ["e2", "e4", ""],
            ["e7", "e5", ""],
            ["f2", "f4", ""],
            ["e5", "f4", ""],
            ["g2", "g3", ""],
            ["f4", "g3", ""],
            ["d1", "e2", ""],
            ["g3", "h2", ""],
            ["e2", "g4", ""],
            ["h2", "g1", "q"],
            ["a2", "a3", ""],
            ["g1", "h2", "q"],
            ]

        await self.play_a_few_moves(moves)