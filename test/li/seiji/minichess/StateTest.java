package li.seiji.minichess;

import li.seiji.helpers.MoveHelper;
import li.seiji.minichess.move.Move;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StateTest {

    @Test //Tests a move that is physically valid (does not follow the rules)
    public void testExecutePhysicallyValidMove() throws InvalidMoveException {
        State state = new State();
        state.initialize();
        Move move = MoveHelper.generateRandomPhysicallyValidMove(state);
        State newState = state.move(move);
        assertEquals(Player.BLACK, newState.turn);
    }

    @Test
    public void testExecutePhysicallyInvalidMove() {
        State state = new State();
        state.initialize();
        Move move = MoveHelper.generateRandomPhysicallyInvalidMove(state);
        try {
            state.move(move);
            fail("Expected InvalidMoveException");
        } catch (InvalidMoveException e) {}
    }


    //TODO: implement a test that checks every invalid move for negative validity?

}
