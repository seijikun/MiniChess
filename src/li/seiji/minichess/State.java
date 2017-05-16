package li.seiji.minichess;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by Lyot on 16.05.2017.
 */
public class State {
    public char board[][] = new char[6][5];
    public Player turn = Player.WHITE;


    /* CONSTRUCTION AND INITIALIZATION */

    @Override
    public State clone() {
        State result = new State();
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                result.board[i][j] = board[i][j];
        result.turn = turn;
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
}
