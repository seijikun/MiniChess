package li.seiji.minichess.figure;

import li.seiji.minichess.move.Move;
import li.seiji.minichess.Player;
import li.seiji.minichess.State;

public class Pawn implements IFigure {

    public static final char identifier = 'p';

    static boolean isMoveValid(State state, Move move) {
        if(!IFigure.isMoveValid(state, move)) return false;
        Player player = Player.parseIdentifier(move.from.getIdentifier(state));

        int deltaY = (player == Player.WHITE) ? -1 : 1;
        boolean isValidStraightMove = IFigure.isStraightMove(move) &&
                IFigure.getMoveDeltaY(move) == deltaY &&
                IFigure.getFieldPlayer(state, move.to) == Player.NONE;

        boolean isValidDiagonalMove = IFigure.getMoveDeltaY(move) == deltaY && IFigure.getAbsMoveDeltaX(move) == 1;

        return (isValidDiagonalMove || isValidStraightMove);
    }

}
