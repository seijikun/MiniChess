package li.seiji.minichess.figure;

import li.seiji.minichess.board.State;
import li.seiji.minichess.move.Move;

public class Knight {

    public static final char identifier = 'n';

    public static boolean isMoveValid(State state, Move move) {
        return (
            (IFigure.getAbsMoveDeltaX(move) == 2 && IFigure.getAbsMoveDeltaY(move) == 1) ||
            (IFigure.getAbsMoveDeltaX(move) == 1 && IFigure.getAbsMoveDeltaY(move) == 2)
        );
    }

}
