package li.seiji.minichess.move;

import li.seiji.minichess.Board;
import li.seiji.minichess.figure.*;

public class MoveValidator {

    public static boolean isMoveValid(Board state, Move move) {
        if(!IFigure.isMoveWithinBounds(state, move)) return false;
        if(IFigure.getFieldPlayer(state, move.to) == state.turn) return false;
        if(IFigure.getFieldPlayer(state, move.from) != state.turn) return false;

        char identifier = Character.toLowerCase(move.from.getIdentifier(state));
        switch(identifier) {
            case Pawn.identifier:
                return Pawn.isMoveValid(state, move);
            case Rook.identifier:
                return Rook.isMoveValid(state, move);
            case Bishop.identifier:
                return Bishop.isMoveValid(state, move);
            case Knight.identifier:
                return Knight.isMoveValid(state, move);
            case King.identifier:
                return King.isMoveValid(state, move);
            case Queen.identifier:
                return Queen.isMoveValid(state, move);
        }
        return false;
    }

}
