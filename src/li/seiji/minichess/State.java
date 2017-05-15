package li.seiji.minichess;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

public class State {

    public char board[][] = new char[6][5];
    public Player turn = Player.WHITE;

    private void initialize() {
        StringReader reader = new StringReader(Board.DEFAULT_BOARD);
        try {
            read(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
