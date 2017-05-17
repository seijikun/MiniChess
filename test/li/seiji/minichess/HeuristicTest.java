package li.seiji.minichess;

import li.seiji.minichess.board.GameState;
import li.seiji.minichess.player.HeuristicPlayer;
import li.seiji.minichess.player.RandomPlayer;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;

public class HeuristicTest {

    @Test
    public void test500Games() throws InvalidMoveException, IOException {
        for(int i = 0; i < 500; ++i) {
            RandomPlayer randomPlayer = new RandomPlayer();
            HeuristicPlayer heuristicPlayer = new HeuristicPlayer();

            Game game = new Game(randomPlayer, heuristicPlayer);
            game.run();

            boolean valid = game.getResult() == GameState.TIE || game.getResult() == GameState.WIN_BLACK;
            assertTrue(valid);
        }
    }

}
