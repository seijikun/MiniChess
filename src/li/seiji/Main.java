package li.seiji;

import li.seiji.minichess.Game;
import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.player.IMCSPlayer;
import li.seiji.minichess.player.RandomPlayer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InvalidMoveException, IOException {
        IMCSPlayer netPlayer = null;
        try {
            netPlayer = new IMCSPlayer("imcs.svcs.cs.pdx.edu", 3589, "lazycat", "31337");
            netPlayer.setOfferGame(Player.BLACK);
            //netPlayer.setGameConfig("12823", Player.BLACK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RandomPlayer randomPlayer = new RandomPlayer();

        Game game = new Game(netPlayer, randomPlayer);
        game.run();
    }
}
