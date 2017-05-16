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
        Board board = new Board();
        board.state.initialize();

        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                HashSet<Move> allMoves = MoveHelper.generateAllPhysicallyPossibleMoves(board.state, new Square(x, y));
                HashSet<Move> validMoves = new HashSet<>(board.getPossibleMoves());

                for(Move move : allMoves) {
                    boolean should = validMoves.contains(move);
                    boolean actual = MoveValidator.isMoveValid(board.state, move);
                    assertEquals(should, actual);
                }
            }
        }
    }

}
