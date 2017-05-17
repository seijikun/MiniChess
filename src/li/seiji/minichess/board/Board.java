package li.seiji.minichess.board;

import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.Square;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.move.MoveGenerator;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Board {

    /* BOARD DEFINITIONS */
    public static final int ROWS = 6;
    public static final int COLUMNS = 5;
    public static final String DEFAULT_BOARD = "kqbnr" + System.lineSeparator() +
            "ppppp" + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "PPPPP" + System.lineSeparator() +
            "RNBQK";

    public State state = new State();

    public void move(Move move) throws InvalidMoveException {
        state = state.move(move);
    }

    /**
     * Get a list of all possible moves for the current player on the current board as defined by state.
     * @return The list of all possible moves that can be done by the current player.
     */
    public List<Move> getPossibleMoves() {
        return state.getPossibleMoves();
    }

    /* SERIALIZATION AND DESERIALIZATION */

    public void prettyPrint() {
        for(int y = 0; y < Board.ROWS; ++y) {
            System.out.print("| " + (Board.ROWS - y) + " |");
            for(int x = 0; x < Board.COLUMNS; ++x) {
                char identifier = state.board[y][x];
                System.out.print('_');
                System.out.print((identifier != '.') ? identifier : '_');
                System.out.print("_|");
            }
            System.out.println();
        }

        System.out.println("|   | a | b | c | d | e |");
    }

}
