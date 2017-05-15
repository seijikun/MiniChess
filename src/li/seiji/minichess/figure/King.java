package li.seiji.minichess.figure;

import li.seiji.minichess.Move;
import li.seiji.minichess.State;

public class King implements IFigure {

    public static final char identifier = 'k';

    static boolean isMoveValid(State state, Move move) {
        if(!IFigure.isMoveWithinBounds(state, move)) return false;
        //TODO: implement
        return true;
    }

}
