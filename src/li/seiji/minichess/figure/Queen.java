package li.seiji.minichess.figure;

import li.seiji.minichess.Move;
import li.seiji.minichess.Player;
import li.seiji.minichess.State;

import java.util.function.Function;

public class Queen implements IFigure {

    public static final char identifier = 'q';

    static boolean isMoveValid(State state, Move move) {
        if(!IFigure.isMoveValid(state, move)) return false;
        Player currentPlayer = Player.parseIdentifier(move.from.getIdentifier(state));

        if(IFigure.isStraightMove(move))
            if(!IFigure.checkStraightIsBlocked(state, move, straightBlockChecker))
                return true;

        //TODO: implement diagonal

        return false;
    }

    //Queen is blocked by any figure that is not an empty field
    private static Function<Player, Boolean> straightBlockChecker = (Player player) -> player != Player.NONE;

}
