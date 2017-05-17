package li.seiji;

import li.seiji.minichess.Game;
import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.logging.ConsoleLogger;
import li.seiji.minichess.player.HeuristicPlayer;
import li.seiji.minichess.player.NegamaxPlayer;
import li.seiji.minichess.player.RandomPlayer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InvalidMoveException, IOException {
//        IMCSPlayer netPlayer = null;
//        try {
//            netPlayer = new IMCSPlayer("imcs.svcs.cs.pdx.edu", 3589, "lazycat", "31337");
//            netPlayer.setAcceptGame("12892", Player.BLACK);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        RandomPlayer randomPlayer = new RandomPlayer();
//
//        Game game = new Game(netPlayer, randomPlayer);
//        game.run();

        int wins = 0;

        for(int i = 0; i < 500; ++i) {
            RandomPlayer randomPlayer = new RandomPlayer();
            NegamaxPlayer negamaxPlayer = new NegamaxPlayer();

            Game game = new Game(randomPlayer, negamaxPlayer);
            game.setLogger(new ConsoleLogger());
            game.run();

            if(game.getResult() == GameState.WIN_BLACK)
                wins++;
        }

        System.out.println("Wins: " + wins);
    }
}
