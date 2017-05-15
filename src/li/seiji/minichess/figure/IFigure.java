package li.seiji.minichess.figure;

import li.seiji.minichess.Move;
import li.seiji.minichess.Square;
import li.seiji.minichess.State;

import java.util.List;

public interface IFigure {

    static void getPossibleMoves(State state, Square square, List<Move> result){}

}
