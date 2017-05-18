package li.seiji.minichess.board;

import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.Square;
import li.seiji.minichess.figure.*;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.move.MoveGenerator;
import li.seiji.minichess.move.MoveValidator;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class State implements Cloneable {
    public char board[][] = new char[6][5];
    public Player turn = Player.WHITE;
    public GameState gameState = GameState.ONGOING;
    public int turnCounter = 0;


    /* CONSTRUCTION AND INITIALIZATION */

    @Override
    public State clone() {
        State result = new State();
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                result.board[i][j] = board[i][j];
        result.turn = turn;
        result.turnCounter = turnCounter;
        return result;
    }

    public void move(Move move) throws InvalidMoveException {
        if(!MoveValidator.isMovePhysicallyValid(this, move)) //only slight validation
            throw new InvalidMoveException(this, move);

        //move figure from move.from to move.to
        move.toOldVal = move.to.getFieldValue(this);
        move.fromOldVal = move.from.getFieldValue(this);
        move.oldGameState = gameState;

        move.to.setIdentifier(this, move.fromOldVal);
        move.from.setIdentifier(this, '.');

        if(Square.toIdenifier(move.toOldVal) == King.identifier) //destination field was king
            gameState = (turn == Player.BLACK) ? GameState.WIN_BLACK : GameState.WIN_WHITE;
        if(Square.toIdenifier(move.fromOldVal) == Pawn.identifier) //we moved a pawn
            checkPawnForTransformation(move);

        if(turn == Player.WHITE) {
            turn = Player.BLACK;
        } else {
            turn = Player.WHITE;
            turnCounter++;
        }
        if(turnCounter >= 40)
            gameState = GameState.TIE;
    }

    public void unmove(Move move) {
        gameState = move.oldGameState;
        move.from.setIdentifier(this, move.fromOldVal);
        move.to.setIdentifier(this, move.toOldVal);
        if(turn == Player.WHITE) {
            turn = Player.BLACK;
            turnCounter--;
        } else {
            turn = Player.WHITE;
        }
    }

    /**
     * Get a list of all possible moves for the current player on the current board as defined by state.
     * @return The list of all possible moves that can be done by the current player.
     */
    public List<Move> getPossibleMoves() {
        ArrayList<Move> result = new ArrayList<>();

        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                char identifier = board[y][x];
                Player player = Player.parseIdentifier(identifier);

                if(identifier != '.' && player == turn)
                    MoveGenerator.moveList(this, result, new Square(x, y));
            }
        }
        if(ThreadLocalRandom.current().nextBoolean())
            Collections.shuffle(result); //avoid draw cycles
        return result;
    }

    public void read(Reader reader) throws IOException {
        for (int y = 0; y < Board.ROWS; ++y) {
            for (int x = 0; x < Board.COLUMNS; ++x) {
                board[y][x] = (char) reader.read();
            }
            reader.skip(System.lineSeparator().length());
        }
    }

    public void initialize() {
        StringReader reader = new StringReader(Board.DEFAULT_BOARD);
        try {
            read(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float calculateScore() {
        float score = 0.0f;

        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                char fieldValue = board[y][x];
                char identifier = Square.getIdentifier(board, x, y);
                if(turn == Player.parseIdentifier(fieldValue))
                    score += getScore(identifier);
                else
                    score -= getScore(identifier);
            }
        }

        return score;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                sb.append(board[y][x]);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public void write(Writer writer) throws IOException {
        writer.write(toString());
    }






    /**
     * Check if the Pawn is at the opposite end of the Field. If so, the pawn gets transformed into a queen.
     * @param move Move the pawn is doing.
     */
    private void checkPawnForTransformation(Move move) {
        int endOfField = (turn == Player.BLACK) ? Board.ROWS - 1 : 0;

        if(move.to.y == endOfField) {
            char queenIdentifier =
                    (turn == Player.BLACK) ? Queen.identifier : Character.toUpperCase(Queen.identifier);

            move.to.setIdentifier(this, queenIdentifier);
        }
    }

    private float getScore(char identifier) {
        switch (identifier) {
            case King.identifier:
                return King.pointScore;
            case Queen.identifier:
                return Queen.pointScore;
            case Rook.identifier:
                return Rook.pointScore;
            case Bishop.identifier:
                return Bishop.pointScore;
            case Knight.identifier:
                return Knight.pointScore;
            case Pawn.identifier:
                return Pawn.pointScore;
            default:
                return 0.0f;
        }
    }
}
