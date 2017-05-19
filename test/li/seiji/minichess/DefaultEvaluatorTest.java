package li.seiji.minichess;

import li.seiji.minichess.board.State;
import li.seiji.minichess.evaluator.DefaultBoardEvaluator;
import li.seiji.minichess.figure.*;
import li.seiji.minichess.move.Move;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class DefaultEvaluatorTest {

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
    public void testStateScore() throws IOException, InvalidMoveException {
        DefaultBoardEvaluator eval = new DefaultBoardEvaluator();
        State state = new State();
        state.initialize();

        float maxScore = King.pointScore + Queen.pointScore + Rook.pointScore + Bishop.pointScore + Knight.pointScore + 5*Pawn.pointScore;

        assertEquals(0.0f, eval.calculate(state, null));

        state.read(new StringReader(testState));
        assertEquals(maxScore, eval.calculate(state, null));

        state.read(new StringReader(testState2));
        assertEquals(-maxScore, eval.calculate(state, null));

        Move[] gameLog = {
                new Move("b2-b3"),
                new Move("c5-c4"),
                new Move("b3-c4"),
                new Move("b6-c5"),
                new Move("c4-b5"),
                new Move("c6-b6"),
                new Move("b5-a6")
        };
        state.initialize();
        for(Move m : gameLog)
            state.move(m);

        float score = eval.calculate(state, null);
        assertEquals(-1010.0f, score);
    }
}
