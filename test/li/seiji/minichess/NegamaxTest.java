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
            NegamaxPlayer neger4 = new NegamaxPlayer(4);

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
            NegamaxPlayer neger4 = new NegamaxPlayer(4);

            Game game = new Game(neger4, randomPlayer);
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
    public void test2vs4DepthWhite() throws InvalidMoveException, IOException {
        int wins = 0;
        int loss = 0;

        for(int i = 0; i < ITERATIONS; ++i) {
            NegamaxPlayer neger2 = new NegamaxPlayer(2);
            NegamaxPlayer neger4 = new NegamaxPlayer(4);

            Game game = new Game(neger4, neger2);
            game.run();

            if(game.getResult() == GameState.WIN_WHITE)
                wins++;
            else if(game.getResult() == GameState.WIN_BLACK)
                loss++;
        }
        assertTrue(wins > ITERATIONS * 0.98);
        assertEquals(0, loss);
    }

    @Test
    public void test2vs4DepthBlack() throws InvalidMoveException, IOException {
        int wins = 0;
        int loss = 0;

        for(int i = 0; i < ITERATIONS; ++i) {
            NegamaxPlayer neger2 = new NegamaxPlayer(2);
            NegamaxPlayer neger4 = new NegamaxPlayer(4);

            Game game = new Game(neger2, neger4);
            game.run();

            if(game.getResult() == GameState.WIN_BLACK)
                wins++;
            else if(game.getResult() == GameState.WIN_WHITE)
                loss++;
            else System.out.println(game.getResult());
        }
        assertTrue(wins > ITERATIONS * 0.98);
        assertEquals(0, loss);
    }

}
