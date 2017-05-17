package li.seiji.minichess.figure;

import li.seiji.minichess.board.State;
import li.seiji.minichess.move.Move;

public class King {

    public static final char identifier = 'k';
    public static final float pointScore = 12.0f;

    public static boolean isMoveValid(State state, Move move) {
        if(IFigure.isStraightMove(move) && IFigure.getStraightMoveLen(move) == 1) return true;
        if(IFigure.isDiagonalMove(move) && IFigure.getDiagonalMoveLen(move) == 1) return true;

        return false;
    }

}
