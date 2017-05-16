package li.seiji.minichess;

import com.sun.media.sound.InvalidFormatException;
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

    public char board[][] = new char[6][5];
    public Player turn = Player.WHITE;

    /* CONSTRUCTION AND INITIALIZATION */

    @Override
    public Board clone() {
        Board result = new Board();
        for(int i=0; i<board.length; i++)
            for(int j=0; j<board[i].length; j++)
                result.board[i][j] = board[i][j];
        result.turn = turn;
        return result;
    }

    public void initialize() {
        StringReader reader = new StringReader(Board.DEFAULT_BOARD);
        try {
            read(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Board move(Move move) throws InvalidMoveException {
        if(!MoveValidator.isMoveValid(this, move))
            throw new InvalidMoveException(this, move);

        Player destSquarePlayer = Player.parseIdentifier(move.to.getIdentifier(this));
        if(destSquarePlayer == turn)
            throw new InvalidMoveException(this, move);

        Board result = clone();
        //move figure from move.from to move.to
        move.to.setIdentifier(result, move.from.getIdentifier(this));
        move.from.setIdentifier(result, '.');


        if(move.from.getIdentifier(this) == Pawn.identifier)
            checkPawnForTransformation(move);

        result.turn = (turn == Player.WHITE) ? Player.BLACK : Player.WHITE;
        return  result;
    }

    private void checkPawnForTransformation(Move move) {
        int endOfField = turn == Player.BLACK ? ROWS : 0;

        if(move.to.y == endOfField) {
            char queenIdentifier =
                    turn == Player.BLACK ? Character.toLowerCase(Queen.identifier) : Character.toUpperCase(Queen.identifier);

            move.to.setIdentifier(this, queenIdentifier);
        }
    }

    /**
     * Get a list of all possible moves for the current player on the current board as defined by state.
     * @param state Board state (defines the board itself and the current player)
     * @return The list of all possible moves that can be done by the current player.
     */
    public static List<Move> getPossibleMoves(Board state) {
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
                sb.append(board[y][x]);
            }
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    public void write(Writer writer) throws IOException {
        writer.write(toString());
    }

    public void read(Reader reader) throws IOException {
        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                board[y][x] = (char)reader.read();
            }
            reader.skip(System.getProperty("line.separator").length());
        }
    }

    public static void prettyPrint(Board state) {
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
