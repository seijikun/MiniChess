package li.seiji.minichess.figure;

import li.seiji.minichess.Board;
import li.seiji.minichess.Move;
import li.seiji.minichess.Player;
import li.seiji.minichess.Square;
import li.seiji.minichess.State;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class Rook implements IFigure {

    public static final char identifier = 'r';

    public static void getPossibleMoves(State state, Square from, Player player, List<Move> result) {
        if(from.x > 0){
            for(int i = from.x-1; i >= 0; i--) {
                result.add(new Move(from, new Square(i, from.y)));
                if (state.board[from.y][i] != '.') break;
            }
        }

        if(from.x < Board.COLUMNS){
            for(int i = from.x+1; i <= Board.COLUMNS; i++) {
                result.add(new Move(from, new Square(i, from.y)));
                if (state.board[from.y][i] != '.') break;
            }
        }

        if(from.y > 0){
            for(int i = from.y-1; i >= 0; i--) {
                result.add(new Move(from, new Square(from.x, i)));
                if (state.board[i][from.x] != '.') break;
            }
        }

        if(from.y < Board.ROWS){
            for(int i = from.y+1; i <= Board.ROWS; i++) {
                result.add(new Move(from, new Square(from.x, i)));
                if (state.board[i][from.x] != '.') break;
            }
        }
    }

}
