package li.seiji.minichess;

import li.seiji.minichess.board.GameState;
import li.seiji.minichess.logging.ConsoleLogger;
import li.seiji.minichess.player.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ThreadedIterativeNegamaxAlphaBetaTest {

    private static final int RAND_ITERATIONS = 50;
    private static final int ITERATIONS = 5;

    @Test
    public void testVsRandomBlack() throws InvalidMoveException, IOException {
        int wins = 0;
        int loss = 0;

        for(int i = 0; i < RAND_ITERATIONS; ++i) {
            RandomPlayer randomPlayer = new RandomPlayer();
            PlayerBase negamaxAlphaBetaPlayer = new ThreadedIterativeNegamaxAlphaBetaPlayer(20);

            Game game = new Game(randomPlayer, negamaxAlphaBetaPlayer);
            game.run();

            if(game.getResult() == GameState.WIN_BLACK)
                wins++;
            else if(game.getResult() == GameState.WIN_WHITE)
                loss++;
        }
        assertTrue(wins > RAND_ITERATIONS * 0.9);
        assertTrue(loss <= 1);
    }

    @Test
    public void testVsRandomWhite() throws InvalidMoveException, IOException {
        int wins = 0;
        int loss = 0;

        for(int i = 0; i < RAND_ITERATIONS; ++i) {
            RandomPlayer randomPlayer = new RandomPlayer();
            PlayerBase negamaxAlphaBetaPlayer = new ThreadedIterativeNegamaxAlphaBetaPlayer(20);

            Game game = new Game(negamaxAlphaBetaPlayer, randomPlayer);
            game.run();

            if(game.getResult() == GameState.WIN_WHITE)
                wins++;
            else if(game.getResult() == GameState.WIN_BLACK)
                loss++;
        }
        assertTrue(wins > ITERATIONS * 0.9);
        assertTrue(loss <= 1);
    }


    @Test
    public void test2vs7secDepthWhite() throws InvalidMoveException, IOException {
        int wins = 0;
        int loss = 0;

        for(int i = 0; i < ITERATIONS; ++i) {
            NegamaxPlayer negamaxPlayer = new NegamaxPlayer(2);
            PlayerBase negamaxAlphaBetaPlayer = new ThreadedIterativeNegamaxAlphaBetaPlayer(20);

            Game game = new Game(negamaxAlphaBetaPlayer, negamaxPlayer);
            game.setLogger(new ConsoleLogger());
            game.run();

            if(game.getResult() == GameState.WIN_WHITE)
                wins++;
            else if(game.getResult() == GameState.WIN_BLACK)
                loss++;

            System.out.println(game.getResult() + " - " + game.getTurns());
        }
        assertTrue(wins > ITERATIONS * 0.6);
        assertEquals(0, loss);
    }

    @Test
    public void test2vs7secDepthBlack() throws InvalidMoveException, IOException {
        int wins = 0;
        int loss = 0;

        for(int i = 0; i < ITERATIONS; ++i) {
            NegamaxPlayer negamaxPlayer = new NegamaxPlayer(2);
            PlayerBase negamaxAlphaBetaPlayer = new ThreadedIterativeNegamaxAlphaBetaPlayer(20);

            Game game = new Game(negamaxPlayer, negamaxAlphaBetaPlayer);
            game.setLogger(new ConsoleLogger());
            game.run();

            if(game.getResult() == GameState.WIN_BLACK)
                wins++;
            else if(game.getResult() == GameState.WIN_WHITE)
                loss++;

            System.out.println(game.getResult() + " - " + game.getTurns());
        }
        assertTrue(wins > ITERATIONS * 0.6);
        assertEquals(0, loss);
    }

}
