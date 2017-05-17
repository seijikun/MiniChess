package li.seiji.minichess.figure;

import li.seiji.minichess.Player;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.Move;

import java.util.function.Function;

public class Bishop {

    public static final char identifier = 'b';
    public static final float pointScore = 4.0f;

    public static boolean isMoveValid(State state, Move move) {
        Player player = Player.parseIdentifier(move.to.getFieldValue(state));

        if(IFigure.isStraightMove(move) && IFigure.getStraightMoveLen(move) == 1 && IFigure.getFieldPlayer(state, move.to) == Player.NONE)
            return true;

        if(IFigure.isDiagonalMove(move) && !IFigure.checkDiagonalIsBlocked(state, move, diagonalBlockChecker))
            return true;

        return false;
    }

    //Bishop is blocked by any figure that is not an empty field
    private static Function<Player, Boolean> diagonalBlockChecker = (Player player) -> player != Player.NONE;

}