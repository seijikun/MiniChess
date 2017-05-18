package li.seiji.minichess;

import com.sun.media.sound.InvalidFormatException;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.Move;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class VictoryDetectionTest {

    @Test
    public void testWeirdConstellation() throws InvalidFormatException, InvalidMoveException {
        State state = new State();
        state.initialize();
        Move[] moves = {
                new Move("e2-e3"),
                new Move("d5-d4"),
                new Move("e3-d4"),
                new Move("e5-d4"),
                new Move("d1-e2"),
                new Move("c5-c4"),
                new Move("e2-e6"),
                new Move("c6-d5"),
                new Move("e6-d5"),
                new Move("b6-c5"),
                new Move("d5-c5"),
                new Move("d6-e4"),
                new Move("c5-d4"),
                new Move("a6-b6")
        };
        for(int i = 0; i < moves.length; ++i) {
            state.move(moves[i]);
            assertEquals(GameState.ONGOING, state.gameState);
        }
    }

}
