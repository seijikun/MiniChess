package li.seiji.minichess.figure;

import li.seiji.minichess.Move;
import li.seiji.minichess.State;

public class King implements IFigure {

    public static final char identifier = 'k';

    static boolean isMoveValid(State state, Move move) {
        if(!IFigure.isMoveValid(state, move)) return false;

        if(IFigure.isStraightMove(move) && IFigure.getStraightMoveLen(move) == 1) return true;
        if(IFigure.isDiagonalMove(move) && IFigure.getDiagonalMoveLen(move) == 1) return true;

        return false;
    }

}
