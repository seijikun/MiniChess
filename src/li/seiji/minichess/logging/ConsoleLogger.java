package li.seiji.minichess.logging;

import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.Move;

/**
 * Created by seiji on 5/17/17.
 */
public class ConsoleLogger implements IGameLogger {

    @Override
    public void start(Board board) {
        board.prettyPrint();
        System.out.println();
    }

    @Override
    public void logMove(Board board, Move move) {
        board.prettyPrint();
        System.out.println();
    }

    @Override
    public void end(Board board) {
        System.out.println("And the winner is: " + board.state.gameState.toString());
    }

    @Override
    public void close() {

    }

}
