package li.seiji.minichess;

import li.seiji.helpers.MoveHelper;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.Move;
import org.junit.Test;

import javax.swing.plaf.synth.SynthTabbedPaneUI;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class StateTest {

    public static final String promotionTestField =
            "....." + System.lineSeparator() +
            "P...." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "...p." + System.lineSeparator() +
            "....." + System.lineSeparator();

    public static final String captureTestField =
            "....." + System.lineSeparator() +
            "..k.." + System.lineSeparator() +
            "...p." + System.lineSeparator() +
            "..P.." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator();

    public static final String gameStateRestoreTestField =
            "kqbnr" + System.lineSeparator() +
            "p..pp" + System.lineSeparator() +
            ".Pp.." + System.lineSeparator() +
            "..P.." + System.lineSeparator() +
            ".P.PP" + System.lineSeparator() +
            "RNBQK";

    @Test
    public void testExecutePhysicallyInvalidMove() {
        Board board = new Board();
        board.state.initialize();
        for(int i = 0; i < 5; ++i) {
            Move move = MoveHelper.generateRandomPhysicallyInvalidMove(board.state);
            try {
                board.move(move);
                fail("Expected InvalidMoveException");
            } catch (InvalidMoveException e) {}
        }
    }

    @Test
    public void testMoveUnmove_Promotion() throws IOException, InvalidMoveException {
        State state = new State();
        state.read(new StringReader(promotionTestField));

        Move move0 = new Move("a5-a6");
        Move move1 = new Move("d2-d1");

        assertEquals('P', new Square("a5").getFieldValue(state));
        assertEquals('.', new Square("a6").getFieldValue(state));
        assertEquals('p', new Square("d2").getFieldValue(state));
        assertEquals('.', new Square("d1").getFieldValue(state));
        assertEquals(0, state.turnCounter);
        assertEquals(Player.WHITE, state.turn);
        state.move(move0);
        assertEquals('.', new Square("a5").getFieldValue(state));
        assertEquals('Q', new Square("a6").getFieldValue(state));
        assertEquals('p', new Square("d2").getFieldValue(state));
        assertEquals('.', new Square("d1").getFieldValue(state));
        assertEquals(0, state.turnCounter);
        assertEquals(Player.BLACK, state.turn);
        state.move(move1);
        assertEquals('.', new Square("a5").getFieldValue(state));
        assertEquals('Q', new Square("a6").getFieldValue(state));
        assertEquals('.', new Square("d2").getFieldValue(state));
        assertEquals('q', new Square("d1").getFieldValue(state));
        assertEquals(1, state.turnCounter);
        assertEquals(Player.WHITE, state.turn);

        state.unmove(move1);
        assertEquals('.', new Square("a5").getFieldValue(state));
        assertEquals('Q', new Square("a6").getFieldValue(state));
        assertEquals('p', new Square("d2").getFieldValue(state));
        assertEquals('.', new Square("d1").getFieldValue(state));
        assertEquals(0, state.turnCounter);
        assertEquals(Player.BLACK, state.turn);
        state.unmove(move0);
        assertEquals('P', new Square("a5").getFieldValue(state));
        assertEquals('.', new Square("a6").getFieldValue(state));
        assertEquals('p', new Square("d2").getFieldValue(state));
        assertEquals('.', new Square("d1").getFieldValue(state));
        assertEquals(0, state.turnCounter);
        assertEquals(Player.WHITE, state.turn);
    }

    @Test
    public void testUnmoveCapturing() throws IOException, InvalidMoveException {
        State state = new State();
        state.read(new StringReader(captureTestField));

        Move captureBlack = new Move("c3-d4");
        Move captureWhite = new Move("c5-d4");

        //do
        assertEquals('P', new Square("c3").getFieldValue(state));
        assertEquals('p', new Square("d4").getFieldValue(state));
        assertEquals('k', new Square("c5").getFieldValue(state));
        assertEquals(0, state.turnCounter);
        assertEquals(Player.WHITE, state.turn);
        state.move(captureBlack);
        assertEquals('.', new Square("c3").getFieldValue(state));
        assertEquals('P', new Square("d4").getFieldValue(state));
        assertEquals('k', new Square("c5").getFieldValue(state));
        assertEquals(0, state.turnCounter);
        assertEquals(Player.BLACK, state.turn);
        state.move(captureWhite);
        assertEquals('.', new Square("c3").getFieldValue(state));
        assertEquals('k', new Square("d4").getFieldValue(state));
        assertEquals('.', new Square("c5").getFieldValue(state));
        assertEquals(1, state.turnCounter);
        assertEquals(Player.WHITE, state.turn);

        //undo
        state.unmove(captureWhite);
        assertEquals('.', new Square("c3").getFieldValue(state));
        assertEquals('P', new Square("d4").getFieldValue(state));
        assertEquals('k', new Square("c5").getFieldValue(state));
        assertEquals(0, state.turnCounter);
        assertEquals(Player.BLACK, state.turn);
        state.unmove(captureBlack);
        assertEquals('P', new Square("c3").getFieldValue(state));
        assertEquals('p', new Square("d4").getFieldValue(state));
        assertEquals('k', new Square("c5").getFieldValue(state));
        assertEquals(0, state.turnCounter);
        assertEquals(Player.WHITE, state.turn);
    }

    @Test
    public void testUnmoveGameStateRestore() throws IOException, InvalidMoveException {
        State state = new State();
        state.read(new StringReader(gameStateRestoreTestField));
        state.turn = Player.BLACK;

        Move move0 = new Move("a5-b4");
        Move move1 = new Move("a1-a6");

        assertEquals('p', new Square("a5").getFieldValue(state));
        assertEquals('P', new Square("b4").getFieldValue(state));
        assertEquals('R', new Square("a1").getFieldValue(state));
        assertEquals('k', new Square("a6").getFieldValue(state));
        assertEquals(GameState.ONGOING, state.gameState);
        assertEquals(0, state.turnCounter);
        assertEquals(Player.BLACK, state.turn);
        state.move(move0);
        assertEquals('.', new Square("a5").getFieldValue(state));
        assertEquals('p', new Square("b4").getFieldValue(state));
        assertEquals('R', new Square("a1").getFieldValue(state));
        assertEquals('k', new Square("a6").getFieldValue(state));
        assertEquals(GameState.ONGOING, state.gameState);
        assertEquals(1, state.turnCounter);
        assertEquals(Player.WHITE, state.turn);
        state.move(move1);
        assertEquals('.', new Square("a5").getFieldValue(state));
        assertEquals('p', new Square("b4").getFieldValue(state));
        assertEquals('.', new Square("a1").getFieldValue(state));
        assertEquals('R', new Square("a6").getFieldValue(state));
        assertEquals(GameState.WIN_WHITE, state.gameState);
        assertEquals(1, state.turnCounter);
        assertEquals(Player.BLACK, state.turn);

        //undo
        state.unmove(move1);
        assertEquals('.', new Square("a5").getFieldValue(state));
        assertEquals('p', new Square("b4").getFieldValue(state));
        assertEquals('R', new Square("a1").getFieldValue(state));
        assertEquals('k', new Square("a6").getFieldValue(state));
        assertEquals(GameState.ONGOING, state.gameState);
        assertEquals(1, state.turnCounter);
        assertEquals(Player.WHITE, state.turn);
        state.unmove(move0);
        assertEquals('p', new Square("a5").getFieldValue(state));
        assertEquals('P', new Square("b4").getFieldValue(state));
        assertEquals('R', new Square("a1").getFieldValue(state));
        assertEquals('k', new Square("a6").getFieldValue(state));
        assertEquals(GameState.ONGOING, state.gameState);
        assertEquals(0, state.turnCounter);
        assertEquals(Player.BLACK, state.turn);
    }

}
