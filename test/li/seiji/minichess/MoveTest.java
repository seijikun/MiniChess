package li.seiji.minichess;

import li.seiji.minichess.move.Move;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MoveTest {

    @Test
    public void testMoveStringParsing() {
        Move move = new Move("c2-e4");
        assertSquareCorrect(move.from, 2, 1);
        assertSquareCorrect(move.to, 4, 3);

        move = new Move("e4-c2");
        assertSquareCorrect(move.from, 4, 3);
        assertSquareCorrect(move.to, 2, 1);
    }



    private static void assertSquareCorrect(Square square, int x, int y) {
        assertEquals(x, square.x);
        assertEquals(y, square.y);
    }

}
