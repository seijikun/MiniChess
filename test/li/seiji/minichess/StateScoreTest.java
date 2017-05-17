package li.seiji.minichess;

import li.seiji.minichess.board.State;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static junit.framework.TestCase.assertEquals;

public class StateScoreTest {

    public static final String testState =
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "PPPPP" + System.lineSeparator() +
            "RNBQK";

    public static final String testState2 =
            "kqbnr" + System.lineSeparator() +
            "ppppp" + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            ".....";

    @Test
    public void testStateScore() throws IOException {
        State state = new State();
        state.initialize();

        assertEquals(state.calculateScore(), 0.0f);

        state.read(new StringReader(testState));
        assertEquals(state.calculateScore(), 37.0f);

        state.read(new StringReader(testState2));
        assertEquals(state.calculateScore(), -37.0f);
    }
}
