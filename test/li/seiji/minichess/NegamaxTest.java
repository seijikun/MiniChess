package li.seiji.minichess;

import li.seiji.minichess.board.GameState;
import li.seiji.minichess.player.NegamaxPlayer;
import li.seiji.minichess.player.RandomPlayer;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NegamaxTest {

    static final int ITERATIONS = 10;

    @Test
    public void testVsRandomBlack() throws InvalidMoveException, IOException {
        int wins = 0;
        int loss = 0;

        for(int i = 0; i < ITERATIONS; ++i) {
            RandomPlayer randomPlayer = new RandomPlayer();
            NegamaxPlayer neger4 = new NegamaxPlayer(2);

            Game game = new Game(randomPlayer, neger4);
            game.run();

            if(game.getResult() == GameState.WIN_BLACK)
                wins++;
            else if(game.getResult() == GameState.WIN_WHITE)
                loss++;
        }
        assertTrue(wins > ITERATIONS * 0.98);
        assertTrue(loss <= 1);
    }

    @Test
    public void testVsRandomWhite() throws InvalidMoveException, IOException {
        int wins = 0;
        int loss = 0;

        for(int i = 0; i < ITERATIONS; ++i) {
            RandomPlayer randomPlayer = new RandomPlayer();
            NegamaxPlayer neger6 = new NegamaxPlayer(2);

            Game game = new Game(neger6, randomPlayer);
            game.run();

            if(game.getResult() == GameState.WIN_WHITE)
                wins++;
            else if(game.getResult() == GameState.WIN_BLACK)
                loss++;
        }
        assertTrue(wins > ITERATIONS * 0.98);
        assertTrue(loss <= 1);
    }


    @Test
    public void test2vs5DepthWhite() throws InvalidMoveException, IOException {
        int wins = 0;
        int loss = 0;

        for(int i = 0; i < ITERATIONS; ++i) {
            NegamaxPlayer negamax2 = new NegamaxPlayer(2);
            NegamaxPlayer negamax6 = new NegamaxPlayer(5);

            Game game = new Game(negamax6, negamax2);
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
            NegamaxPlayer negamax2 = new NegamaxPlayer(2);
            NegamaxPlayer negamax5 = new NegamaxPlayer(5);

            Game game = new Game(negamax2, negamax5);
            game.run();

            if(game.getResult() == GameState.WIN_BLACK)
                wins++;
            else if(game.getResult() == GameState.WIN_WHITE)
                loss++;

            System.out.println(game.getResult() + " - " + game.getTurns());
        }
        assertTrue(wins > ITERATIONS * 0.9);
        assertEquals(0, loss);
    }

}
