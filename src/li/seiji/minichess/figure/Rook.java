package li.seiji.minichess.figure;

import li.seiji.minichess.Player;
import li.seiji.minichess.State;
import li.seiji.minichess.move.Move;

import java.util.function.Function;

public class Rook {

    public static final char identifier = 'r';

    public static boolean isMoveValid(State state, Move move) {
        if(IFigure.isStraightMove(move) && !IFigure.checkStraightIsBlocked(state, move, straightBlockChecker))
            return true;

        return false;
    }

    //Rook is blocked by any figure that is not an empty field
    private static Function<Player, Boolean> straightBlockChecker = (Player player) -> player != Player.NONE;

}
