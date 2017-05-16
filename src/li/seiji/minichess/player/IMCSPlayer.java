package li.seiji.minichess.player;

import li.seiji.minichess.Player;
import li.seiji.minichess.Board;
import li.seiji.minichess.imcs.Client;
import li.seiji.minichess.move.Move;

import java.io.IOException;

public class IMCSPlayer implements IPlayer {

    private Client imcs;
    private String gameId = null;
    private Player player = Player.NONE;

    public IMCSPlayer(String domain, int port, String username, String password) throws IOException {
        imcs = new Client(domain, Integer.toString(port), username, password);
    }

    public void setGameConfig(String gameId, Player player) {
        this.gameId = gameId;
        this.player = player;
    }


    @Override
    public void start() {
        if(gameId == null || player == Player.NONE)
            throw new RuntimeException("Accepting gameId and player has not been set.");
        try {
            imcs.accept(gameId, player.toString().charAt(0));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Move getMove(Board state) {
        try {
            return new Move(imcs.getMove());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setMove(Board state, Move move) {
        try {
            imcs.sendMove(move.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}