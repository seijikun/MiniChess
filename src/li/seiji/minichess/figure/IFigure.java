package li.seiji.minichess.figure;

import li.seiji.minichess.Board;
import li.seiji.minichess.Move;
import li.seiji.minichess.State;

public interface IFigure {

    static boolean isMoveValid(State state, Move move) { return false; }

    static boolean isMoveWithinBounds(State state, Move move) {
        return (move.to.x >= 0 && move.to.x < Board.COLUMNS && move.to.y >= 0 && move.to.y < Board.ROWS);
    }

}
