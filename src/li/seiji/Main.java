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
        int wins = 0;
        int loss = 0;

        for(int i = 0; i < 20; ++i) {
            NegamaxPlayer neger2 = new NegamaxPlayer(2);
            NegamaxPlayer neger6 = new NegamaxPlayer(4);

            Game game = new Game(neger6, neger2);
            game.run();

            if(game.getResult() == GameState.WIN_WHITE) {
                wins++;
                System.out.println("win");
            }
            else if(game.getResult() == GameState.WIN_BLACK) {
                loss++;
                System.out.println("loss");
            } else
                System.out.println("tie");
        }

        System.out.println("------");

        for(int i = 0; i < 20; ++i) {
            NegamaxPlayer neger2 = new NegamaxPlayer(2);
            NegamaxPlayer neger6 = new NegamaxPlayer(4);

            Game game = new Game(neger2, neger6);
            game.run();

            if(game.getResult() == GameState.WIN_BLACK) {
                wins++;
                System.out.println("win");
            }
            else if(game.getResult() == GameState.WIN_WHITE) {
                loss++;
                System.out.println("loss");
            } else
                System.out.println("tie");
            System.out.println(game.getTurns());
        }

        System.out.println("Wins: " + wins);
        System.out.println("Loss: " + loss);
    }
}
