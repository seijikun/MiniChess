package li.seiji.minichess;

import li.seiji.helpers.MoveHelper;
import li.seiji.minichess.move.Move;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StateTest {

    @Test
    public void testExecutePhysicallyInvalidMove() {
        Board board = new Board();
        board.initialize();
        for(int i = 0; i < 5; ++i) {
            Move move = MoveHelper.generateRandomPhysicallyInvalidMove(board.state);
            try {
                board.move(move);
                fail("Expected InvalidMoveException");
            } catch (InvalidMoveException e) {}
        }
    }

}
