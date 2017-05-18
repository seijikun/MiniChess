package li.seiji.minichess;

import li.seiji.minichess.board.State;
import li.seiji.minichess.figure.Pawn;
import li.seiji.minichess.figure.Queen;
import li.seiji.minichess.move.Move;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static junit.framework.TestCase.assertEquals;

public class PawnTransformationTest {

    public static final String testState =
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            ".P..." + System.lineSeparator() +
            "....." + System.lineSeparator();

    public static final String testState2 =
            "....." + System.lineSeparator() +
            "...p." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator();

    @Test
    public void testPawnTransformation() throws IOException, InvalidMoveException {
        State state = new State();

        // White Pawn to Queen
        state.read(new StringReader(testState));
        Move move = new Move("b2-b3");
        state.turn = Player.WHITE;
        state.move(move);
        assertEquals(new Square("b3").getIdentifier(state), Pawn.identifier);
        move = new Move("b3-b4");
        state.turn = Player.WHITE;
        state.move(move);
        assertEquals(new Square("b4").getIdentifier(state), Pawn.identifier);
        move = new Move("b4-b5");
        state.turn = Player.WHITE;
        state.move(move);
        assertEquals(new Square("b5").getIdentifier(state), Pawn.identifier);
        move = new Move("b5-b6");
        state.turn = Player.WHITE;
        state.move(move);
        assertEquals(new Square("b6").getIdentifier(state), Queen.identifier);

        // Black Pawn to Queen
        state.read(new StringReader(testState2));
        move = new Move("d5-d4");
        state.turn = Player.BLACK;
        state.move(move);
        assertEquals(new Square("d4").getIdentifier(state), Pawn.identifier);
        move = new Move("d4-d3");
        state.turn = Player.BLACK;
        state.move(move);
        assertEquals(new Square("d3").getIdentifier(state), Pawn.identifier);
        move = new Move("d3-d2");
        state.turn = Player.BLACK;
        state.move(move);
        assertEquals(new Square("d2").getIdentifier(state), Pawn.identifier);
        move = new Move("d2-d1");
        state.turn = Player.BLACK;
        state.move(move);
        assertEquals(new Square("d1").getIdentifier(state), Queen.identifier);
    }
}
