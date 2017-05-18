package li.seiji.minichess;

import li.seiji.minichess.board.GameState;
import li.seiji.minichess.player.NegamaxAlphaBetaPlayer;
import li.seiji.minichess.player.NegamaxPlayer;
import li.seiji.minichess.player.RandomPlayer;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NegamaxAlphaBetaTest {

    static final int RAND_ITERATIONS = 50;
    static final int ITERATIONS = 10;

    @Test
    public void testVsRandomBlack() throws InvalidMoveException, IOException {
        int wins = 0;
        int loss = 0;

        for(int i = 0; i < RAND_ITERATIONS; ++i) {
            RandomPlayer randomPlayer = new RandomPlayer();
            NegamaxAlphaBetaPlayer negamaxAlphaBetaPlayer = new NegamaxAlphaBetaPlayer(2);

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
            NegamaxAlphaBetaPlayer negamaxAlphaBetaPlayer = new NegamaxAlphaBetaPlayer(2);

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
    public void test2vs5DepthWhite() throws InvalidMoveException, IOException {
        int wins = 0;
        int loss = 0;

        for(int i = 0; i < ITERATIONS; ++i) {
            NegamaxPlayer negamaxPlayer = new NegamaxPlayer(2);
            NegamaxAlphaBetaPlayer negamaxAlphaBetaPlayer = new NegamaxAlphaBetaPlayer(6);

            Game game = new Game(negamaxAlphaBetaPlayer, negamaxPlayer);
            game.run();

            if(game.getResult() == GameState.WIN_WHITE)
                wins++;
            else if(game.getResult() == GameState.WIN_BLACK)
                loss++;

            System.out.println(game.getResult() + " - " + game.getTurns());
        }
        assertTrue(wins > ITERATIONS * 0.9);
        assertEquals(0, loss);
    }

    @Test
    public void test2vs5DepthBlack() throws InvalidMoveException, IOException {
        int wins = 0;
        int loss = 0;

        for(int i = 0; i < ITERATIONS; ++i) {
            NegamaxPlayer negamaxPlayer = new NegamaxPlayer(2);
            NegamaxAlphaBetaPlayer negamaxAlphaBetaPlayer = new NegamaxAlphaBetaPlayer(6);

            Game game = new Game(negamaxPlayer, negamaxAlphaBetaPlayer);
            game.run();

            if(game.getResult() == GameState.WIN_BLACK)
                wins++;
            else if(game.getResult() == GameState.WIN_WHITE)
                loss++;

            System.out.println(game.getResult() + " - " + game.getTurns());
        }
        assertTrue(wins > ITERATIONS * 0.5);
        assertEquals(0, loss);
    }

}
