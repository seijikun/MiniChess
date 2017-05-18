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
        Client client = new Client("imcs.svcs.cs.pdx.edu",  3589);

        for(int i = 0; i < 30; ++i) {
            try{
                IMCSGame serverGame = client.waitForGame(g -> !g.isRunning && !g.ownerName.equals("TacklingDummy"));

                IMCSPlayer netPlayer = new IMCSPlayer("imcs.svcs.cs.pdx.edu",  3589, "lazycat", "31337");
                char ownPlayer = netPlayer.accept(serverGame.gameId);

                NegamaxIterativeAlphaBetaPlayer player = new NegamaxIterativeAlphaBetaPlayer(8);
                Game game = null;

                if(ownPlayer == 'W') {
                    game = new Game(player, netPlayer);
                } else {
                    game = new Game(netPlayer, player);
                }
                game.setLogger(new ConsoleLogger());
                game.run();

                System.out.println("########################");
                System.out.println("####### end game #######");
                System.out.println("########################");
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}
