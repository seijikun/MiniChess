package li.seiji.minichess.figure;

import li.seiji.minichess.Move;
import li.seiji.minichess.State;

public class Knight implements IFigure {

    public static final char identifier = 'n';

    static boolean isMoveValid(State state, Move move) {
        if(!IFigure.isMoveValid(state, move)) return false;

        return (
            (IFigure.getAbsMoveDeltaX(move) == 2 && IFigure.getAbsMoveDeltaY(move) == 1) ||
            (IFigure.getAbsMoveDeltaX(move) == 1 && IFigure.getAbsMoveDeltaY(move) == 2)
        );
    }

}
