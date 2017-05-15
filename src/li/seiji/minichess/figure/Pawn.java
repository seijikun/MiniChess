package li.seiji.minichess.figure;

import li.seiji.minichess.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class Pawn implements IFigure {

    public static final char identifier = 'p';

    public static void getPossibleMoves(State state, Square from, Player player, List<Move> result) {
        if(player == Player.BLACK) {
            if(from.x > 0 && state.board[from.y+1][from.x-1] != '.')
                result.add(new Move(from, new Square(from.y+1, from.x-1)));
            else if(from.x < Board.COLUMNS && state.board[from.y+1][from.x+1] != '.')
                result.add(new Move(from, new Square(from.y+1, from.x+1)));
            else
                result.add(new Move(from, new Square(from.x, from.y+1)));
        }
        else {
            if(from.x > 0 && state.board[from.y-1][from.x-1] != '.')
                result.add(new Move(from, new Square(from.y-1, from.x-1)));
            else if(from.x < Board.COLUMNS && state.board[from.y-1][from.x+1] != '.')
                result.add(new Move(from, new Square(from.y-1, from.x+1)));
            else
                result.add(new Move(from, new Square(from.x, from.y-1)));
        }
    }

}
