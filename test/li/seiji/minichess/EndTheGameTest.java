package li.seiji.minichess;

import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.player.NegamaxPlayer;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static junit.framework.TestCase.assertEquals;

public class EndTheGameTest {

    public static final String testBoard =
            "....r" + System.lineSeparator() +
            "..k.." + System.lineSeparator() +
            ".P..." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "....." + System.lineSeparator() +
            "...R." + System.lineSeparator();

    @Test
    public void testEndTheGameFast() throws IOException, InvalidMoveException {
        Board board = new Board();
        board.state = new State();
        board.state.turn = Player.WHITE;
        board.state.read(new StringReader(testBoard));

        NegamaxPlayer player = new NegamaxPlayer(4);
        player.start(Player.WHITE);

        Move endMove = player.getMove(board);
        assertEquals("b4-c5", endMove.toString());
    }

}
