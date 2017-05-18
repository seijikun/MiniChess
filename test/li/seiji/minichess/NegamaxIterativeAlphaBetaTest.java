package li.seiji.minichess;

import li.seiji.minichess.board.GameState;
import li.seiji.minichess.player.NegamaxIterativeAlphaBetaPlayer;
import li.seiji.minichess.player.NegamaxPlayer;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NegamaxIterativeAlphaBetaTest {

    static final int ITERATIONS = 10;

    @Test
    public void test2vs5DepthWhite() throws InvalidMoveException, IOException {
        int wins = 0;
        int loss = 0;

        for(int i = 0; i < ITERATIONS; ++i) {
            NegamaxPlayer negamaxPlayer = new NegamaxPlayer(2);
            NegamaxIterativeAlphaBetaPlayer negamaxAlphaBetaPlayer = new NegamaxIterativeAlphaBetaPlayer(Player.WHITE, 5);

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
            NegamaxIterativeAlphaBetaPlayer negamaxAlphaBetaPlayer = new NegamaxIterativeAlphaBetaPlayer(Player.BLACK, 5);

            Game game = new Game(negamaxPlayer, negamaxAlphaBetaPlayer);
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
