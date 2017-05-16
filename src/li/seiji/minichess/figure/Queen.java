package li.seiji.minichess.figure;

import li.seiji.minichess.Player;
import li.seiji.minichess.State;
import li.seiji.minichess.move.Move;

import java.util.function.Function;

public class Queen {

    public static final char identifier = 'q';

    public static boolean isMoveValid(State state, Move move) {
        Player currentPlayer = Player.parseIdentifier(move.from.getIdentifier(state));

        if(IFigure.isStraightMove(move) && !IFigure.checkStraightIsBlocked(state, move, blockChecker))
            return true;

        if(IFigure.isDiagonalMove(move) && !IFigure.checkDiagonalIsBlocked(state, move, blockChecker))
            return true;

        return false;
    }

    //Queen is blocked by any figure that is not an empty field
    private static Function<Player, Boolean> blockChecker = (Player player) -> player != Player.NONE;

}
