package li.seiji.minichess.figure;

import li.seiji.minichess.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class Queen implements IFigure {

    public static final char identifier = 'q';

    public static void getPossibleMoves(State state, Square from, Player player, List<Move> result) {
        for(int x = from.x-1; x >= 0; x--) {
            result.add(new Move(from, new Square(x, from.y)));
            if (state.board[from.y][x] != '.') break;
        }
        for(int x = from.x+1; x < Board.COLUMNS; x++) {
            result.add(new Move(from, new Square(x, from.y)));
            if (state.board[from.y][x] != '.') break;
        }
        for(int y = from.y-1; y >= 0; y--) {
            result.add(new Move(from, new Square(from.x, y)));
            if (state.board[y][from.x] != '.') break;
        }
        for(int y = from.y+1; y < Board.ROWS; y++) {
            result.add(new Move(from, new Square(from.x, y)));
            if (state.board[y][from.x] != '.') break;
        }

        for(int x = from.x-1, y = from.y-1; x >= 0 && y >= 0; x--, y--) {
            result.add(new Move(from, new Square(x, y)));
            if (state.board[x][y] != '.') break;
        }
        for(int x = from.x-1, y = from.y+1; x >= 0 && y < Board.ROWS; x--, y++) {
            result.add(new Move(from, new Square(x, y)));
            if (state.board[x][y] != '.') break;
        }
        for(int x = from.x+1, y = from.y+1; x < Board.COLUMNS && y < Board.ROWS; x++, y++) {
            result.add(new Move(from, new Square(x, y)));
            if (state.board[x][y] != '.') break;
        }
        for(int x = from.x+1, y = from.y-1; x < Board.COLUMNS && y >= 0; x++, y--) {
            result.add(new Move(from, new Square(x, y)));
            if (state.board[x][y] != '.') break;
        }
    }

}
