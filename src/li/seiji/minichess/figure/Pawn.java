package li.seiji.minichess.figure;

import li.seiji.minichess.Player;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.Move;

public class Pawn {

    public static final char identifier = 'p';
    public static final float pointScore = 1.0f;

    public static boolean isMoveValid(State state, Move move) {
        Player player = Player.parseIdentifier(move.from.getFieldValue(state));

        int deltaY = (player == Player.WHITE) ? -1 : 1;
        boolean isValidStraightMove = IFigure.isStraightMove(move) &&
                IFigure.getMoveDeltaY(move) == deltaY &&
                IFigure.getFieldPlayer(state, move.to) == Player.NONE;

        boolean isValidDiagonalMove = (
                (IFigure.getMoveDeltaY(move) == deltaY && IFigure.getAbsMoveDeltaX(move) == 1) &&
                IFigure.isDestinationFieldAnEnemy(state, move, player)
        );

        return (isValidDiagonalMove || isValidStraightMove);
    }

}
