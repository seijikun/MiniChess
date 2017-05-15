package li.seiji.minichess.figure;

import li.seiji.minichess.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class Bishop implements IFigure {

    public static final char identifier = 'b';

    public static void getPossibleMoves(State state, Square from, Player player, List<Move> result) {
        if(from.x > 0)
            result.add(new Move(from, new Square(from.x-1, from.y)));
        if(from.x < Board.COLUMNS)
            result.add(new Move(from, new Square(from.x+1, from.y)));
        if(from.y > 0)
            result.add(new Move(from, new Square(from.x, from.y-1)));
        if(from.y < Board.ROWS)
            result.add(new Move(from, new Square(from.x, from.y+1)));

        for(int x = from.x-1, y = from.y-1; x > 0 && y > 0; x--, y--)
            result.add(new Move(from, new Square(x, y)));

        for(int x = from.x-1, y = from.y+1; x > 0 && y < Board.ROWS; x--, y++)
            result.add(new Move(from, new Square(x, y)));

        for(int x = from.x+1, y = from.y+1; x < Board.COLUMNS && y < Board.ROWS; x++, y++)
            result.add(new Move(from, new Square(x, y)));

        for(int x = from.x+1, y = from.y-1; x < Board.COLUMNS && y > 0; x++, y--)
            result.add(new Move(from, new Square(x, y)));
    }

}