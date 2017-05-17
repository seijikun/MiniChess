package li.seiji.minichess;

import li.seiji.minichess.board.State;
import li.seiji.minichess.move.Move;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

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

    public static final String gameLog =
            "b2-b3" + System.lineSeparator() +
            "c5-c4" + System.lineSeparator() +
            "b3-c4" + System.lineSeparator() +
            "b6-c5" + System.lineSeparator() +
            "c4-b5" + System.lineSeparator() +
            "c6-b6" + System.lineSeparator() +
            "b5-a6";

    @Test
    public void testStateScore() throws IOException, InvalidMoveException {
        State state = new State();
        state.initialize();

        assertEquals(0.0f, state.calculateScore());

        state.read(new StringReader(testState));
        assertEquals(37.0f, state.calculateScore());

        state.read(new StringReader(testState2));
        assertEquals(-37.0f, state.calculateScore());

        GameLogger logger = new GameLogger(GameLogger.Mode.READ);
        List<Move> gameMoves = logger.readString(gameLog);
        state.initialize();
        for(Move m : gameMoves)
            state = state.move(m);

        float score = state.calculateScore();
        assertEquals(Float.MAX_VALUE / (float)state.turnCounter, score);
    }
}
