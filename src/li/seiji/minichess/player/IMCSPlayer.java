package li.seiji.minichess.player;

import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.imcs.Client;
import li.seiji.minichess.move.Move;

import java.io.IOException;

public class IMCSPlayer extends PlayerBase {

    private Client imcs;

    public IMCSPlayer(String domain, int port, String username, String password) throws IOException {
        this(new Client(domain, Integer.toString(port)));
        imcs.login(username, password);
    }

    public IMCSPlayer(Client client) {
        this.imcs = client;
    }


    public char accept(String gameId) throws IOException {
        return imcs.accept(gameId);
    }

    public void offer(Player ownPlayer) throws IOException {
        imcs.offerGameAndWait(ownPlayer.toString().charAt(0));
    }


    @Override
    public void start(Player color) {}

    @Override
    public Move getMove(Board board) {
        System.gc(); //use opponent's play time to run the GC
        try {
            return new Move(imcs.getMove());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setMove(Board board, Move move) {
        try {
            imcs.sendMove(move.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void end() {
        try {
            imcs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
