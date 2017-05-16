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

    public static final String testState3 =
            "....." + System.lineSeparator() +
            ".p.p." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            ".p.p." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator();

    @Test
    public void testCheckStraightIsBlocked() throws IOException {
        Board board = new Board();
        board.state.initialize();
        //bottom to up
        Move move = new Move("a2-a3");
        assertFalse(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("a2-a4");
        assertFalse(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("a2-a5");
        assertFalse(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("a2-a6");
        assertTrue(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));

        //up to bottom
        move = new Move("a5-a4");
        assertFalse(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("a5-a3");
        assertFalse(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("a5-a2");
        assertFalse(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("a5-a1");
        assertTrue(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));

        //left to right
        board.state.read(new StringReader(testState));
        move = new Move("a4-b4");
        assertFalse(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("a4-c4");
        assertFalse(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("a4-d4");
        assertFalse(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("a4-e4");
        assertTrue(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));

        //right to left
        board.state.read(new StringReader(testState2));
        move = new Move("e4-d4");
        assertFalse(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("e4-c4");
        assertFalse(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("e4-b4");
        assertFalse(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("e4-a4");
        assertTrue(IFigure.checkStraightIsBlocked(board.state, move, player -> player != Player.NONE));
    }

    @Test
    public void testCheckDiagonalIsBlocked() throws IOException {
        Board board = new Board();
        board.state.read(new StringReader(testState3));
        //bottom-left to top-right
        Move move = new Move("b3-c4");
        assertFalse(IFigure.checkDiagonalIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("b3-d5");
        assertFalse(IFigure.checkDiagonalIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("b3-e6");
        assertTrue(IFigure.checkDiagonalIsBlocked(board.state, move, player -> player != Player.NONE));

        //top-right to bottom-left
        move = new Move("d5-c4");
        assertFalse(IFigure.checkDiagonalIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("d5-b3");
        assertFalse(IFigure.checkDiagonalIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("d5-a2");
        assertTrue(IFigure.checkDiagonalIsBlocked(board.state, move, player -> player != Player.NONE));

        // bottom-right to top-left
        move = new Move("d3-c4");
        assertFalse(IFigure.checkDiagonalIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("d3-b5");
        assertFalse(IFigure.checkDiagonalIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("d3-a6");
        assertTrue(IFigure.checkDiagonalIsBlocked(board.state, move, player -> player != Player.NONE));

        //top-left to bottom-right
        move = new Move("b5-c4");
        assertFalse(IFigure.checkDiagonalIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("b5-d3");
        assertFalse(IFigure.checkDiagonalIsBlocked(board.state, move, player -> player != Player.NONE));
        move = new Move("b5-e2");
        assertTrue(IFigure.checkDiagonalIsBlocked(board.state, move, player -> player != Player.NONE));
    }

}
