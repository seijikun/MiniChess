package li.seiji;

import li.seiji.minichess.Game;
import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.board.State;
import li.seiji.minichess.evaluator.DefaultBoardEvaluator;
import li.seiji.minichess.evaluator.ExtendedBoardEvaluator;
import li.seiji.minichess.imcs.Client;
import li.seiji.minichess.imcs.IMCSGame;
import li.seiji.minichess.logging.ConsoleLogger;
import li.seiji.minichess.player.*;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InvalidMoveException, IOException, InterruptedException {
        warmup();
        Scanner scanner = new Scanner(System.in);

        while(true) {
            try {
                System.out.print("What to do: ");
                String[] cmd = scanner.nextLine().split(" ");

                if(cmd.length == 2 && cmd[0].equals("accept")) {
                    System.out.println("Accepting game: " + cmd[1]);
                    playAccept(cmd[1]);
                } else if(cmd.length == 1 && cmd[0].equals("offer")) {
                    System.out.println("Offering game...");
                    playOffer();
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    private static void playOffer() throws IOException, InvalidMoveException {
        IMCSPlayer netPlayer = new IMCSPlayer("imcs.svcs.cs.pdx.edu",  3589, "lazycat", "31337");
        char ownPlayer = netPlayer.offer();

        ThreadedIterativeNegamaxAlphaBetaPlayer player = new ThreadedIterativeNegamaxAlphaBetaPlayer(25);
        Game game;

        if(ownPlayer == 'W') {
            game = new Game(player, netPlayer);
        } else {
            game = new Game(netPlayer, player);
        }
        game.setLogger(new ConsoleLogger());
        game.run();
    }

    private static void playAccept(String gameId) throws IOException, InvalidMoveException {
        IMCSPlayer netPlayer = new IMCSPlayer("imcs.svcs.cs.pdx.edu",  3589, "lazycat", "31337");
        char ownPlayer = netPlayer.accept(gameId);

        ThreadedIterativeNegamaxAlphaBetaPlayer player = new ThreadedIterativeNegamaxAlphaBetaPlayer(25);
        Game game;

        if(ownPlayer == 'W') {
            game = new Game(player, netPlayer);
        } else {
            game = new Game(netPlayer, player);
        }
        game.setLogger(new ConsoleLogger());
        game.run();
    }


    private static void warmup() throws InvalidMoveException {
        System.out.println("Warming up");
        Board board = new Board();

        ThreadedIterativeNegamaxAlphaBetaPlayer playerWhite = new ThreadedIterativeNegamaxAlphaBetaPlayer(25);
        ThreadedIterativeNegamaxAlphaBetaPlayer playerBlack = new ThreadedIterativeNegamaxAlphaBetaPlayer(25);
        playerWhite.start(Player.WHITE);
        playerBlack.start(Player.BLACK);

        for(int i = 0; i < 4; ++i) {
            if(i % 2 == 0) {
                board.move(playerWhite.getMove(board));
            } else {
                board.move(playerBlack.getMove(board));
            }
        }

        playerWhite.end();
        playerBlack.end();
    }

}
