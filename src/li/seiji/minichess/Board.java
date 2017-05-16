package li.seiji.minichess;

import li.seiji.minichess.figure.King;
import li.seiji.minichess.figure.Pawn;
import li.seiji.minichess.figure.Queen;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.move.MoveGenerator;
import li.seiji.minichess.move.MoveValidator;

import java.io.IOException;
import java.io.StringReader;
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
        List<Move> result = new ArrayList<>();

        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                char identifier = state.board[y][x];
                Player player = Player.parseIdentifier(identifier);

                if(identifier != '.' && player == state.turn)
                    MoveGenerator.moveList(state, result, new Square(x, y));
            }
        }

        return result;
    }

    /* SERIALIZATION AND DESERIALIZATION */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                sb.append(state.board[y][x]);
            }
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    public void write(Writer writer) throws IOException {
        writer.write(toString());
    }

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
