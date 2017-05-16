package li.seiji.minichess;

import li.seiji.minichess.move.Move;

public class InvalidMoveException extends Exception {

    public InvalidMoveException(Board state, Move move) {
        super("Player " + state.turn.toString() + " can not move from " + move.from.toString() + " to " + move.to.toString());
    }

}
