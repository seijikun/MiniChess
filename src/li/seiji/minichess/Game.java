package li.seiji.minichess;

import li.seiji.minichess.move.Move;
import li.seiji.minichess.player.IPlayer;

public class Game {

    private Board board = new Board();
    private IPlayer white;
    private IPlayer black;

    public Game(IPlayer white, IPlayer black) {
        board.initialize();
        this.white = white;
        this.black = black;
    }

    public void run() throws InvalidMoveException {
        white.start();
        black.start();

        while(board.state.turn != Player.NONE) {
            IPlayer turnPlayer;
            IPlayer otherPlayer;
            if(board.state.turn == Player.WHITE) {
                turnPlayer = white; otherPlayer = black;
            } else {
                turnPlayer = black; otherPlayer = white;
            }

            Move move = turnPlayer.getMove(board);
            board.state = board.move(move);
            otherPlayer.setMove(board, move);
        }
    }

}
