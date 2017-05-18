package li.seiji;

import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.player.*;

import java.util.HashMap;
import java.util.Map;

public class SpeedComparision {

    public static final int TRIES = 10;

    public static void main(String[] args) throws InvalidMoveException {
        Board board = new Board();

        Map<String, IPlayer> players = new HashMap<>();
        players.put("HeuristicPlayer", new HeuristicPlayer());
        players.put("NegamaxPlayer", new NegamaxPlayer(8));
        players.put("NegamaxAlphaBetaPlayer", new NegamaxAlphaBetaPlayer(8));
        players.put("ThreadedNegamaxAlphaBetaPlayer", new ThreadedNegamaxAlphaBetaPlayer(8));

        for(Map.Entry<String, IPlayer> player : players.entrySet()) {
            player.getValue().start(Player.WHITE);
            System.out.print(player.getKey() + ": ");

            long sum = 0;
            for(int i = 0; i < TRIES; ++i) {
                long start = System.nanoTime();
                player.getValue().getMove(board);
                long end = System.nanoTime();
                sum += (end - start);
            }

            float time = (float)sum / (float)TRIES / 1000.0f;
            System.out.println(time + " ms");

            player.getValue().end();
        }

    }

}
