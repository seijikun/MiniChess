package li.seiji.minichess;

import li.seiji.minichess.figure.IFigure;
import li.seiji.minichess.move.Move;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class IFigureTest {

    public static final String testState = "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "p..p." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator();
    public static final String testState2 = "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            ".p..q" + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator();

    @Test
    public void testCheckStraightIsBlocked() throws IOException {
        State state = new State();
        state.initialize();
        //bottom to up
        Move move = new Move("a2-a3");
        assertFalse(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));
        move = new Move("a2-a4");
        assertFalse(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));
        move = new Move("a2-a5");
        assertFalse(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));
        move = new Move("a2-a6");
        assertTrue(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));

        //up to bottom
        move = new Move("a2-a3");
        assertFalse(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));
        move = new Move("a2-a4");
        assertFalse(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));
        move = new Move("a2-a5");
        assertFalse(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));
        move = new Move("a2-a6");
        assertTrue(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));

        //left to right
        state.read(new StringReader(testState));
        move = new Move("a3-b3");
        assertFalse(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));
        move = new Move("a3-c3");
        assertFalse(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));
        move = new Move("a3-d3");
        assertFalse(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));
        move = new Move("a3-e3");
        assertTrue(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));

        //right to left
        state.read(new StringReader(testState2));
        move = new Move("e3-d3");
        assertFalse(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));
        move = new Move("e3-c3");
        assertFalse(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));
        move = new Move("e3-b3");
        assertFalse(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));
        move = new Move("e3-a3");
        assertTrue(IFigure.checkStraightIsBlocked(state, move, player -> player != Player.NONE));
    }

    @Test
    public void testCheckDiagonalIsBlocked() {

    }

}
