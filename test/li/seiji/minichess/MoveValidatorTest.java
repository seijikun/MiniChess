package li.seiji.minichess;

import li.seiji.helpers.MoveHelper;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.move.MoveValidator;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class MoveValidatorTest {

    @Test
    public void testMoveValidity() {
        Board state = new Board();
        state.initialize();

        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                HashSet<Move> allMoves = MoveHelper.generateAllPhysicallyPossibleMoves(state, new Square(x, y));
                HashSet<Move> validMoves = new HashSet<>(Board.getPossibleMoves(state));

                for(Move move : allMoves) {
                    boolean should = validMoves.contains(move);
                    boolean actual = MoveValidator.isMoveValid(state, move);
                    assertEquals(should, actual);
                }
            }
        }
    }

}
