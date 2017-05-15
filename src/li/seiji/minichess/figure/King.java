package li.seiji.minichess.figure;

import li.seiji.minichess.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class King implements IFigure {

    public static final char identifier = 'k';

    public static void getPossibleMoves(State state, Square from, Player player, List<Move> result) {
        if(from.x > 0)
            result.add(new Move(from, new Square(from.x-1, from.y)));
        if(from.x < Board.COLUMNS)
            result.add(new Move(from, new Square(from.x+1, from.y)));
        if(from.y > 0)
            result.add(new Move(from, new Square(from.x, from.y-1)));
        if(from.y < Board.ROWS)
            result.add(new Move(from, new Square(from.x, from.y+1)));

        if(from.x > 0 && from.y > 0)
            result.add(new Move(from, new Square(from.x-1, from.y-1)));
        if(from.x > 0 && from.y < Board.ROWS)
            result.add(new Move(from, new Square(from.x-1, from.y+1)));
        if(from.x < Board.COLUMNS && from.y < Board.ROWS)
            result.add(new Move(from, new Square(from.x+1, from.y+1)));
        if(from.x < Board.COLUMNS && from.y > 0)
            result.add(new Move(from, new Square(from.x-1, from.y+1)));
    }

}
