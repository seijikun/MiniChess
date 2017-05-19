package li.seiji.minichess;

import li.seiji.minichess.board.GameState;
import li.seiji.minichess.player.HeuristicPlayer;
import li.seiji.minichess.player.OptimizedHeuristicPlayer;
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
        assertTrue(heuristicWins > TESTED_GAMES * 0.98);
    }

    @Test
    public void testCalculateScoreVSOptimizedCalculateScoreBlack() throws InvalidMoveException, IOException {
        int heuristicWins = 0;
        int heuristicOptWins = 0;
        int ties = 0;

        for(int i = 0; i < TESTED_GAMES; ++i) {
            OptimizedHeuristicPlayer optimizedHeuristicPlayer = new OptimizedHeuristicPlayer();
            HeuristicPlayer heuristicPlayer = new HeuristicPlayer();

            Game game = new Game(heuristicPlayer, optimizedHeuristicPlayer);
            game.run();

            if(game.getResult() == GameState.WIN_WHITE)
                heuristicWins++;
            if(game.getResult() == GameState.WIN_BLACK)
                heuristicOptWins++;
            if(game.getResult() == GameState.TIE)
                ties++;
        }
        System.out.println("Black: OH=" + heuristicOptWins + " H=" + heuristicWins + " Ties=" + ties);
        assertTrue(heuristicOptWins > heuristicWins);
    }

    @Test
    public void testCalculateScoreVSOptimizedCalculateScoreWhite() throws InvalidMoveException, IOException {
        int heuristicWins = 0;
        int heuristicOptWins = 0;
        int ties = 0;

        for(int i = 0; i < TESTED_GAMES; ++i) {
            OptimizedHeuristicPlayer optimizedHeuristicPlayer = new OptimizedHeuristicPlayer();
            HeuristicPlayer heuristicPlayer = new HeuristicPlayer();

            Game game = new Game(optimizedHeuristicPlayer, heuristicPlayer);
            game.run();

            if(game.getResult() == GameState.WIN_BLACK)
                heuristicWins++;
            if(game.getResult() == GameState.WIN_WHITE)
                heuristicOptWins++;
            if(game.getResult() == GameState.TIE)
                ties++;
        }
        System.out.println("White: OH=" + heuristicOptWins + " H=" + heuristicWins + " Ties=" + ties);
        assertTrue(heuristicOptWins > heuristicWins);
    }
}
