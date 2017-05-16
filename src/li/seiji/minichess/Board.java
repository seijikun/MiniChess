package li.seiji.minichess;

import com.sun.media.sound.InvalidFormatException;
import li.seiji.minichess.figure.King;
import li.seiji.minichess.figure.Pawn;
import li.seiji.minichess.figure.Queen;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.move.MoveGenerator;
import li.seiji.minichess.move.MoveValidator;

import java.io.IOException;
import java.io.Reader;
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

    public void initialize() {
        StringReader reader = new StringReader(Board.DEFAULT_BOARD);
        try {
            state.read(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(Move move) throws InvalidMoveException {
        if(!MoveValidator.isMoveValid(state, move))
            throw new InvalidMoveException(state, move);

        Player destSquarePlayer = Player.parseIdentifier(move.to.getIdentifier(state));
        if(destSquarePlayer == state.turn)
            throw new InvalidMoveException(state, move);

        State result = state.clone();
        //move figure from move.from to move.to
        move.to.setIdentifier(result, move.from.getIdentifier(state));
        move.from.setIdentifier(result, '.');


        if(move.from.getIdentifier(state) == Pawn.identifier)
            checkPawnForTransformation(move);
        if(move.to.getIdentifier(state) == King.identifier)
            //TODO King has been captured, finish the Game.

        result.turn = (state.turn == Player.WHITE) ? Player.BLACK : Player.WHITE;
        state = result;
    }

    /**
     * Check if the Pawn is at the opposite end of the Field. If so, the pawn gets transformed into a queen.
     * @param move Move the pawn is doing.
     */
    private void checkPawnForTransformation(Move move) {
        int endOfField = state.turn == Player.BLACK ? ROWS : 0;

        if(move.to.y == endOfField) {
            char queenIdentifier =
                    state.turn == Player.BLACK ? Character.toLowerCase(Queen.identifier) : Character.toUpperCase(Queen.identifier);

            move.to.setIdentifier(state, queenIdentifier);
        }
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
