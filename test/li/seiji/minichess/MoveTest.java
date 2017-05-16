package li.seiji.minichess;

import li.seiji.minichess.move.Move;
import org.junit.Test;

import java.util.HashSet;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class MoveTest {

    @Test
    public void testMoveStringParsing() {
        for(int y = 0; y < 250; ++y) {
            for(int x = 0; x < 250; ++x) {
                Move move = new Move(new Square(x, y), new Square(250-x, 250-y));
                Move parsedMove = new Move(move.toString());
                assertEquals(move.toString(), parsedMove.toString());
            }
        }
    }


    @Test
    public void testHashAndEquality() {
        HashSet<Move> moves = new HashSet<>();
        for(int y = 0; y < 250; ++y) {
            for(int x = 0; x < 250; ++x) {
                moves.add(new Move(new Square(x, y), new Square(250-x, 250-y)));
            }
        }

        //test that the HashSet can find them
        for(Move move : moves) {
            assertTrue(moves.contains(move));
        }
    }


    private static void assertSquareCorrect(Square square, int x, int y) {
        assertEquals(x, square.x);
        assertEquals(y, square.y);
    }

}
