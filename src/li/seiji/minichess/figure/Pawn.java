package li.seiji.minichess.figure;

import li.seiji.minichess.Move;
import li.seiji.minichess.State;

public class Pawn implements IFigure {

    public static final char identifier = 'p';

    static boolean isMoveValid(State state, Move move) {
        if(!IFigure.isMoveWithinBounds(state, move)) return false;
        //TODO: implement
        return true;
    }

}
