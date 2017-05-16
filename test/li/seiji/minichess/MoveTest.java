package li.seiji.minichess;

import com.sun.media.sound.InvalidFormatException;
import li.seiji.minichess.move.Move;
import org.junit.Test;

import java.util.HashSet;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class MoveTest {

    @Test
    public void testMoveStringParsing() throws InvalidFormatException {
        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                Move move = new Move(new Square(x, y), new Square(Board.COLUMNS-x - 1, Board.ROWS-y - 1));
                Move parsedMove = new Move(move.toString());
                assertEquals(move.toString(), parsedMove.toString());
            }
        }
    }


    @Test
    public void testHashAndEquality() {
        HashSet<Move> moves = new HashSet<>();
        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                moves.add(new Move(new Square(x, y), new Square(Board.COLUMNS - x - 1, Board.ROWS - y - 1)));
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
