package li.seiji.minichess;

import li.seiji.helpers.DataHelper;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.move.MoveValidator;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class MoveGeneratorTest {

    @Test
    public void testPossibleMoveValidity() throws InvalidMoveException {
        for(int run = 0; run < 500; ++run) {
            State state = new State();
            state.initialize();

            for(int i = 0; i < 40; ++i) {
                List<Move> moves = Board.getPossibleMoves(state);
                if(moves.size() > 0) {
                    for(Move move : moves)
                        assertTrue(MoveValidator.isMoveValid(state, move));
                    state = state.move(DataHelper.selectRandom(moves));
                }
            }
        }
    }

}
