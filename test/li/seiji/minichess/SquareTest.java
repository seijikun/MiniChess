package li.seiji.minichess;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SquareTest {

    @Test
    public void testAutoPositionStringParsing() {
        String[][] positions = new String[Board.ROWS][Board.COLUMNS];
        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                char[] posStr = {
                        (char)((int)'a' + x),
                        (char)((int)'1' + y)
                }; //construct position strings like: "c4"
                positions[y][x] = new String(posStr);
            }
        }

        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                Square square = new Square(positions[y][x]);
                assertSquareCorrect(square, x, y);
            }
        }
    }

    @Test
    public void testPositionStringParsing() {
        Square square;

        square = new Square("c4");
        assertSquareCorrect(square, 2, 3);
        square = new Square("a6");
        assertSquareCorrect(square, 0, 5);
        square = new Square("e1");
        assertSquareCorrect(square, 4, 0);
    }


    private static void assertSquareCorrect(Square square, int x, int y) {
        assertEquals(x, square.x);
        assertEquals(y, square.y);
    }

}
