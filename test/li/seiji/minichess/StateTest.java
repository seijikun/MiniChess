package li.seiji.minichess;

import li.seiji.helpers.MoveHelper;
import li.seiji.minichess.move.Move;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StateTest {

    @Test
    public void testExecutePhysicallyInvalidMove() {
        Board state = new Board();
        state.initialize();
        for(int i = 0; i < 5; ++i) {
            Move move = MoveHelper.generateRandomPhysicallyInvalidMove(state);
            try {
                state.move(move);
                fail("Expected InvalidMoveException");
            } catch (InvalidMoveException e) {}
        }
    }

}
