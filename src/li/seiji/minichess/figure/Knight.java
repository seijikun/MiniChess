package li.seiji.minichess.figure;

import li.seiji.minichess.Move;
import li.seiji.minichess.Player;
import li.seiji.minichess.Square;
import li.seiji.minichess.State;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class Knight implements IFigure {

    public static final char identifier = 'n';

    public static void getPossibleMoves(State state, Square from, Player player, List<Move> result) {
        throw new NotImplementedException(); //TODO: implement
    }

}
