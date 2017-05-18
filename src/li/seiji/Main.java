package li.seiji;

import li.seiji.minichess.Game;
import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.board.State;
import li.seiji.minichess.imcs.Client;
import li.seiji.minichess.imcs.IMCSGame;
import li.seiji.minichess.logging.ConsoleLogger;
import li.seiji.minichess.player.*;

import java.io.IOException;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws InvalidMoveException, IOException, InterruptedException {
//        Client client = new Client("imcs.svcs.cs.pdx.edu",  3589);
//        IMCSGame game = client.waitForGame(g ->
//            (!g.isRunning && g.ownerName.equals("TacklingDummy") && g.reservedPlayer == 'W')
//        );
//

        IMCSPlayer netPlayer = new IMCSPlayer("imcs.svcs.cs.pdx.edu",  3589, "lazycat", "31337");
        netPlayer.setOfferGame(Player.WHITE);

        NegamaxAlphaBetaPlayer player = new NegamaxAlphaBetaPlayer(7);
        Game game = new Game(player, netPlayer);
        game.setLogger(new ConsoleLogger());
        game.run();
    }

}
