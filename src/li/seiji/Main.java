package li.seiji;

import li.seiji.minichess.Game;
import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.board.State;
import li.seiji.minichess.imcs.Client;
import li.seiji.minichess.imcs.IMCSGame;
import li.seiji.minichess.logging.ConsoleLogger;
import li.seiji.minichess.player.HeuristicPlayer;
import li.seiji.minichess.player.IMCSPlayer;
import li.seiji.minichess.player.NegamaxMultiThreadPlayer;
import li.seiji.minichess.player.NegamaxPlayer;

import java.io.IOException;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws InvalidMoveException, IOException, InterruptedException {
        Client client = new Client("imcs.svcs.cs.pdx.edu",  3589);
        IMCSGame game = client.waitForGame(g ->
            (!g.isRunning && g.ownerName.equals("TacklingDummy") && g.reservedPlayer == 'W')
        );
        System.out.println(game);
    }

}
