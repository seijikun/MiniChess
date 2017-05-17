package li.seiji.minichess;

import li.seiji.minichess.board.State;
import li.seiji.minichess.move.Move;

public class InvalidMoveException extends Exception {

    public InvalidMoveException(State state, Move move) {
        super("Player " + state.turn.toString() + " can not move from " + move.from.toString() + " to " + move.to.toString());
    }

}
