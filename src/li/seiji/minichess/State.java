package li.seiji.minichess;

import li.seiji.minichess.move.Move;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

public class State implements Cloneable {

    public char board[][] = new char[6][5];
    public Player turn = Player.WHITE;


    public State move(Move move) throws InvalidMoveException {
        Player destSquarePlayer = Player.parseIdentifier(move.to.getIdentifier(this));
        if(destSquarePlayer == turn)
            throw new InvalidMoveException(this, move);

        State result = clone();
        //move figure from move.from to move.to
        move.to.setIdentifier(result, move.from.getIdentifier(this));
        move.from.setIdentifier(result, '.');
        result.turn = (turn == Player.WHITE) ? Player.BLACK : Player.WHITE;
        return  result;
    }

    public State move(String moveString) {
        Move move = new Move(moveString);
        //TODO implement
        throw new NotImplementedException();
    }





    /* CONSTRUCTION AND INITIALIZATION */

    @Override
    public State clone() {
        State result = new State();
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

}
