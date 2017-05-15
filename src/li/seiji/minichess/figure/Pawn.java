package li.seiji.minichess.figure;

import li.seiji.minichess.Move;
import li.seiji.minichess.Player;
import li.seiji.minichess.State;

public class Pawn implements IFigure {

    public static final char identifier = 'p';

    static boolean isMoveValid(State state, Move move) {
        if(!IFigure.isMoveWithinBounds(state, move)) return false;
        Player player = Player.parseIdentifier(move.from.getIdentifier(state));

        boolean isValidStraightMove = IFigure.isStraightMove(move) && (
                    (player == Player.WHITE && IFigure.getMoveDeltaY(move) == -1) ||
                    (player == Player.BLACK && IFigure.getMoveDeltaY(move) == 1)
                ) && IFigure.getFieldPlayer(state, move.to) == Player.NONE;

        boolean isValidDiagonalMove = (
                (player == Player.WHITE && IFigure.getMoveDeltaY(move) == 1 && IFigure.getAbsMoveDeltaX(move) == 1) ||
                (player == Player.BLACK && IFigure.getMoveDeltaY(move) == -1 && IFigure.getAbsMoveDeltaX(move) == 1)
        );

        return (isValidDiagonalMove || isValidStraightMove);
    }

}
