package li.seiji.minichess;

import li.seiji.minichess.move.Move;
import li.seiji.minichess.player.IPlayer;

import java.io.IOException;

public class Game {

    private Board board = new Board();
    private IPlayer white;
    private IPlayer black;

    public Game(IPlayer white, IPlayer black) {
        board.state.initialize();
        this.white = white;
        this.black = black;
    }

    public void run() throws InvalidMoveException, IOException {
        white.start();
        black.start();
        board.prettyPrint();

        GameLogger logger = null;
        try {
            logger = new GameLogger(GameLogger.Mode.WRITE);

            while(board.state.gameState == GameState.ONGOING) {
                IPlayer turnPlayer;
                IPlayer otherPlayer;
                if(board.state.turn == Player.WHITE) {
                    turnPlayer = white; otherPlayer = black;
                } else {
                    turnPlayer = black; otherPlayer = white;
                }

                Move move = turnPlayer.getMove(board);
                logger.logMove(move);
                board.move(move);
                otherPlayer.setMove(board, move);

                board.prettyPrint();
            }
        }
        finally {
            if(logger != null)
                logger.close();
        }



        System.out.println("And the winner is: " + board.state.gameState.toString());
    }

}
