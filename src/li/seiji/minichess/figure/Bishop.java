package li.seiji.minichess.figure;

import li.seiji.minichess.Move;
import li.seiji.minichess.Player;
import li.seiji.minichess.State;

import java.util.function.Function;

public class Bishop implements IFigure {

    public static final char identifier = 'b';

    static boolean isMoveValid(State state, Move move) {
        if(!IFigure.isMoveValid(state, move)) return false;
        Player player = Player.parseIdentifier(move.to.getIdentifier(state));

        if(IFigure.isStraightMove(move) && IFigure.getStraightMoveLen(move) == 1 && !IFigure.isDestinationFieldAnEnemy(state, move, player))
            return true;

        if(IFigure.isDiagonalMove(move) && !IFigure.checkDiagonalIsBlocked(state, move, diagonalBlockChecker))
            return true;

        return true;
    }

    //Bishop is blocked by any figure that is not an empty field
    private static Function<Player, Boolean> diagonalBlockChecker = (Player player) -> player != Player.NONE;

}