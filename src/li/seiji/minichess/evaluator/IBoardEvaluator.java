package li.seiji.minichess.evaluator;

import li.seiji.minichess.board.State;
import li.seiji.minichess.move.Move;

public interface IBoardEvaluator {

    float calculate(State state, Move move);

}
