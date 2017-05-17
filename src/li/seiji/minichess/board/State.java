package li.seiji.minichess.board;

import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.Square;
import li.seiji.minichess.figure.*;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.move.MoveGenerator;
import li.seiji.minichess.move.MoveValidator;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

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

    public State move(Move move) throws InvalidMoveException {
        if(!MoveValidator.isMoveValid(this, move))
            throw new InvalidMoveException(this, move);

        Player destSquarePlayer = Player.parseIdentifier(move.to.getFieldValue(this));
        if(destSquarePlayer == this.turn)
            throw new InvalidMoveException(this, move);

        State result = clone();

        if(result.turnCounter >= 40)
            result.gameState = GameState.TIE;
        if(move.to.getIdentifier(result) == King.identifier)
            result.gameState = (result.turn == Player.BLACK) ? GameState.WIN_BLACK : GameState.WIN_WHITE;

        //move figure from move.from to move.to
        move.to.setIdentifier(result, move.from.getFieldValue(result));
        move.from.setIdentifier(result, '.');

        if(move.to.getIdentifier(result) == Pawn.identifier)
            checkPawnForTransformation(result, move);

        result.turn = (result.turn == Player.WHITE) ? Player.BLACK : Player.WHITE;
        result.turnCounter++;
        return result;
    }

    /**
     * Get a list of all possible moves for the current player on the current board as defined by state.
     * @return The list of all possible moves that can be done by the current player.
     */
    public List<Move> getPossibleMoves() {
        List<Move> result = new ArrayList<>();

        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                char identifier = board[y][x];
                Player player = Player.parseIdentifier(identifier);

                if(identifier != '.' && player == turn)
                    MoveGenerator.moveList(this, result, new Square(x, y));
            }
        }

        return result;
    }

    public void read(Reader reader) throws IOException {
        for (int y = 0; y < Board.ROWS; ++y) {
            for (int x = 0; x < Board.COLUMNS; ++x) {
                board[y][x] = (char) reader.read();
            }
            reader.skip(System.getProperty("line.separator").length());
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

        if(gameState.isDefinitiveWin()) {
            if(gameState.ordinal() == turn.ordinal()) // I win
                return Float.MAX_VALUE / (float)turnCounter;

            return -Float.MAX_VALUE / (float)turnCounter;
        }

        int sign = (turn == Player.BLACK) ? 1 : -1;

        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                char fieldValue = board[y][x];
                char identifier = Character.toLowerCase(board[y][x]);
                if(Player.parseIdentifier(fieldValue) == Player.BLACK)
                    score += sign * getScore(identifier);
                else if(Player.parseIdentifier(fieldValue) == Player.WHITE)
                    score += sign * (-1) * getScore(identifier);
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
    private void checkPawnForTransformation(State newState, Move move) {
        int endOfField = (newState.turn == Player.BLACK) ? Board.ROWS - 1 : 0;

        if(move.to.y == endOfField) {
            char queenIdentifier =
                    newState.turn == Player.BLACK ? Queen.identifier : Character.toUpperCase(Queen.identifier);

            move.to.setIdentifier(newState, queenIdentifier);
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
