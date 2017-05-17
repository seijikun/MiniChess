package li.seiji.minichess;

import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.logging.IGameLogger;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.player.IPlayer;

import java.io.IOException;

public class Game {

    private Board board = new Board();
    private IPlayer white;
    private IPlayer black;
    private IGameLogger logger = null;

    public Game(IPlayer white, IPlayer black) {
        board.state.initialize();
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
        white.start(Player.WHITE);
        black.start(Player.BLACK);
        if(logger != null) logger.start(board);

        try {

            while(board.state.gameState == GameState.ONGOING) {
                IPlayer turnPlayer;
                IPlayer otherPlayer;
                if(board.state.turn == Player.WHITE) {
                    turnPlayer = white; otherPlayer = black;
                } else {
                    turnPlayer = black; otherPlayer = white;
                }

                Move move = turnPlayer.getMove(board);
                board.move(move);
                otherPlayer.setMove(board, move);

                if(logger != null) logger.logMove(board, move);
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
