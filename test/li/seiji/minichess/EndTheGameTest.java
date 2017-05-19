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
    
    public static final String testBoard2 =
            "..Q.." + System.lineSeparator() +
            ".k..." + System.lineSeparator() +
            "p...." + System.lineSeparator() +
            "P.p.Q" + System.lineSeparator() +
            "....P" + System.lineSeparator() +
            "..K..";

    @Test
    public void testEndTheGameFast0() throws IOException, InvalidMoveException {
        runEndGameFast(testBoard, Player.WHITE, "b4-c5", 0);
        runEndGameFast(testBoard2, Player.WHITE, "c6-b5", 34);
    }



    private void runEndGameFast(String boardStr, Player turn, String bestEndMove, int turnCounter) throws InvalidMoveException, IOException {
        Board board = new Board();
        board.state = new State();
        board.state.turn = turn;
        board.state.turnCounter = turnCounter;
        board.state.read(new StringReader(boardStr));

        NegamaxPlayer player = new NegamaxPlayer(4);
        player.start(Player.WHITE);

        Move endMove = player.getMove(board);
        assertEquals(bestEndMove, endMove.toString());
    }

}
