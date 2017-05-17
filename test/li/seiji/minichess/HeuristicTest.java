package li.seiji.minichess;

import li.seiji.minichess.board.GameState;
import li.seiji.minichess.player.HeuristicPlayer;
import li.seiji.minichess.player.RandomPlayer;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;

public class HeuristicTest {

    public static final int TESTED_GAMES = 10000;

    @Test
    public void test500Games() throws InvalidMoveException, IOException {
        int heuristicWins = 0;

        for(int i = 0; i < TESTED_GAMES; ++i) {
            RandomPlayer randomPlayer = new RandomPlayer();
            HeuristicPlayer heuristicPlayer = new HeuristicPlayer();

            Game game = new Game(randomPlayer, heuristicPlayer);
            game.run();

            if(game.getResult() == GameState.WIN_BLACK)
                heuristicWins++;
        }
        System.out.println(heuristicWins);
        assertTrue(heuristicWins > TESTED_GAMES * 0.95);
    }

}
