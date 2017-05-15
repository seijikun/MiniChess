package li.seiji.minichess.figure;

import li.seiji.minichess.Move;
import li.seiji.minichess.Player;
import li.seiji.minichess.Square;
import li.seiji.minichess.State;

import java.util.List;

public interface IFigure {

    static void getPossibleMoves(State state, Square from, Player player, List<Move> result){}

}
