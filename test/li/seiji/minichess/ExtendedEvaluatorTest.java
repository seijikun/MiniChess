package li.seiji.minichess;

import li.seiji.minichess.board.GameState;
import li.seiji.minichess.evaluator.ExtendedBoardEvaluator;
import li.seiji.minichess.logging.ConsoleLogger;
import li.seiji.minichess.player.PlayerBase;
import li.seiji.minichess.player.RandomPlayer;
import li.seiji.minichess.player.ThreadedIterativeNegamaxAlphaBetaPlayer;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class ExtendedEvaluatorTest {

    private static final int ITERATIONS = 2;

    @Test
    public void testExtendedEvaluator() throws InvalidMoveException, IOException {
        int wins = 0;
        int loss = 0;

        for(int i = 0; i < ITERATIONS; ++i) {
            PlayerBase normalPlayer = new ThreadedIterativeNegamaxAlphaBetaPlayer(20);
            PlayerBase extendedPlayer = new ThreadedIterativeNegamaxAlphaBetaPlayer(20);
            extendedPlayer.setEvaluator(new ExtendedBoardEvaluator());

            Game game = new Game(normalPlayer, extendedPlayer);
            game.setLogger(new ConsoleLogger());
            game.run();

            if(game.getResult() == GameState.WIN_BLACK)
                wins++;
            else if(game.getResult() == GameState.WIN_WHITE)
                loss++;
        }
        assertTrue(wins > ITERATIONS * 0.9);
        assertTrue(loss <= 1);
    }

}
