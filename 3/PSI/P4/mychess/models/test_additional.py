from django.test import TestCase
from django.contrib.auth import get_user_model
from .models import ChessGame, ChessMove, Player
from channels.testing import ChannelsLiveServerTestCase
from rest_framework.authtoken.models import Token
from .consumers import ChessConsumer
from django.contrib.auth import get_user_model
from channels.testing import WebsocketCommunicator
from django.urls import path
from channels.routing import URLRouter
from channels.db import database_sync_to_async
import chess

User = get_user_model()

# Tests adicionales de models
# Autor: Eduardo Junoy Ortega                        
class ModelTests(TestCase):
    def setUp(self):
        self.player1 = Player.objects.create(username='player1', rating=1500)
        self.player2 = Player.objects.create(username='player2', rating=1600)
        self.game = ChessGame.objects.create(
            whitePlayer=self.player1,
            blackPlayer=self.player2,
            status='active',
            board_state=ChessGame.DEFAULT_BOARD_STATE,
            timeControl='10+5'
        )

    def test_101_invalid_en_passant(self):
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
    
    def test_102_invalid_promotion(self):
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
    
    def test_103_invalid_move_from(self):
        move = ChessMove(
            game=self.game,
            player=self.player1,
            move_from='z9',
            move_to='e4'
        )
        with self.assertRaises(ValueError):
            move.save()


User = get_user_model()
application = URLRouter([
    path("ws/play/<int:gameID>/", ChessConsumer.as_asgi()),
])

# Tests adicionales de consumers
# Autor: Eduardo Junoy Ortega    
class ConsumerTests(ChannelsLiveServerTestCase):
    def setUp(self):
        self.white_user = User.objects.create_user(
            username='white', password='testpassword')
        self.black_user = User.objects.create_user(
            username='black', password='testpassword')
        self.white_token, _ = Token.objects.get_or_create(
            user=self.white_user)
        self.black_token, _ = Token.objects.get_or_create(
            user=self.black_user)
        self.white_token.save()
        self.black_token.save()

        self.white_token_key = self.white_token.key
        self.black_token_key = self.black_token.key

        self.game = ChessGame.objects.create(
            whitePlayer=self.white_user)
        self.game.save()  # single player
        self.game2 = ChessGame.objects.create(
            whitePlayer=self.white_user,
            blackPlayer=self.black_user,
            status='active')
        self.game2.save()  # two players
              
    async def test_104_foolsmate(self):
        moves = [
            ('f2', 'f3', ''),
            ('e7', 'e5', ''),
            ('g2', 'g4', ''),
            ('d8', 'h4', ''),
        ]
        await self.play_a_few_moves(moves)
    
    async def test_105_anastasia_mate(self):
        moves = [
            ('g2', 'g4', ''),
            ('e7', 'e5', ''),
            ('f2', 'f3', ''),
            ('d8', 'h4', ''),
        ]
        await self.play_a_few_moves(moves)        

    async def test_106_greco_mate(self):
        moves = [
            ('e2', 'e4', ''),
            ('e7', 'e5', ''),
            ('f2', 'f4', ''),
            ('d8', 'h4', ''),
        ]
        await self.play_a_few_moves(moves)

    
    async def connect_and_verify(self, gameID, token_key):
            communicator = WebsocketCommunicator(
                application, f"/ws/play/{gameID}/?{token_key}")
            connected, subprotocol = await communicator.connect()
            self.assertTrue(connected)
            response = await communicator.receive_json_from()
            return response, communicator
    
    async def play_a_few_moves(self, moves):
        """Play a few moves and check that the consumer works
        as it should
        """

        # When you try to run game.save()
        # you are trying to commit a transaction.
        # And this cannot happen until the end of the
        # test because TestCase wraps each test within
        # a transaction. It waits until the end of the
        # test to create this object. So you need to
        # use database_sync_to_async to run the save
        # method in the event loop but this seems to
        # conflict with the communicator open

        def saveGame(game):
            game.save()

        gameID = self.game.id
        response1, communicator1 = await self.connect_and_verify(
            gameID, self.white_token_key)
        # Check mensage sent back to the client
        self.assertEqual(response1["type"], "game")
        self.assertEqual(response1["message"], "OK")
        self.assertEqual(response1["status"], ChessGame.PENDING)
        print("status1", response1["status"])

        # close comunicator
        await communicator1.disconnect()

        # update game status
        self.game.blackPlayer = self.black_user
        self.game.status = 'active'
        await database_sync_to_async(saveGame)(self.game)

        # reopen comunicator
        response1, communicator1 = await self.connect_and_verify(
            gameID, self.white_token_key)

        print("saveGame", self.game, flush=True)

        response2, communicator2 = await self.connect_and_verify(
            gameID, self.black_token_key)
        # this second connection from communicator2 returns a message
        # for comunicator1
        response3 = await communicator1.receive_json_from()

        self.assertEqual(response2["type"], "game")
        self.assertEqual(response2["message"], "OK")
        self.assertEqual(response2["status"], ChessGame.ACTIVE)
        print("status2", response2["status"])

        self.assertEqual(response3["type"], "game")
        self.assertEqual(response3["message"], "OK")
        self.assertEqual(response3["status"], ChessGame.ACTIVE)
        print("status3", response3["status"])

        # Initialize the board state
        board_state = chess.STARTING_FEN

        # Play moves
        board = chess.Board(board_state)
        for move in moves:
            # Determine which player's turn it is
            current_communicator = communicator1 if board.turn == chess.WHITE\
                else communicator2
            current_playerID = self.white_user.id if board.turn == chess.WHITE\
                else self.black_user.id
            print("send move", move)
            move_data = {
                "type": "move",
                "from": move[0],
                "to": move[1],
                "playerID": current_playerID,
                "promotion": move[2],
            }

            # Send the move data
            await current_communicator.send_json_to(move_data)

            # Receive and validate the response
            response = await communicator1.receive_json_from()
            self.assertEqual(response["type"], "move")
            response = await communicator2.receive_json_from()
            self.assertEqual(response["type"], "move")
            board.push(chess.Move.from_uci(move[0] + move[1] + move[2]))

        # Disconnect both players
        await communicator1.disconnect()
        await communicator2.disconnect()

        