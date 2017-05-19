package li.seiji.minichess;

import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.logging.IGameLogger;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.player.PlayerBase;

import java.io.IOException;

public class Game {

    private Board board = null;
    private PlayerBase white;
    private PlayerBase black;
    private IGameLogger logger = null;

    public Game(PlayerBase white, PlayerBase black) {
        this.white = white;
        this.black = black;
    }

    public void setLogger(IGameLogger logger) {
        this.logger = logger;
    }

    public GameState getResult() {
        return board.state.gameState;
    }

    public int getTurns() { return board.state.turnCounter; }

    public void run() throws InvalidMoveException, IOException {
        board = new Board();

        white.start(Player.WHITE);
        black.start(Player.BLACK);
        if(logger != null) logger.start(board, white, black);

        try {

            while(board.state.gameState == GameState.ONGOING) {
                PlayerBase turnPlayer;
                PlayerBase otherPlayer;
                if(board.state.turn == Player.WHITE) {
                    turnPlayer = white; otherPlayer = black;
                } else {
                    turnPlayer = black; otherPlayer = white;
                }

                long startTime = System.nanoTime();
                Move move = turnPlayer.getMove(board);
                long endTime = System.nanoTime();
                board.move(move);
                otherPlayer.setMove(board, move);

                if(logger != null) logger.logMove(board, move, (endTime - startTime));
            }
        } finally {
            if(logger != null) {
                logger.end(board);
                logger.close();
            }
            white.end();
            black.end();
        }
    }

}
