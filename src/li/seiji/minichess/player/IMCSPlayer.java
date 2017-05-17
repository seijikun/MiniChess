package li.seiji.minichess.player;

import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.imcs.Client;
import li.seiji.minichess.move.Move;

import java.io.IOException;

public class IMCSPlayer implements IPlayer {

    public enum IMCSGameMode {
        NONE,
        ACCEPT,
        OFFER
    }

    private Client imcs;

    private IMCSGameMode gameMode = IMCSGameMode.NONE;
    private String gameId = null;
    private Player player = Player.NONE;

    public IMCSPlayer(String domain, int port, String username, String password) throws IOException {
        this(new Client(domain, Integer.toString(port)));
        imcs.login(username, password);
    }

    public IMCSPlayer(Client client) {
        this.imcs = client;
    }

    public void setOfferGame(Player ownPlayer) throws IOException {
        player = ownPlayer;
        gameMode = IMCSGameMode.OFFER;
    }

    public void setAcceptGame(String gameId, Player ownPlayer) {
        this.gameId = gameId;
        this.player = ownPlayer;
        gameMode = IMCSGameMode.ACCEPT;
    }


    @Override
    public void start(Player color) {
        if(gameMode == IMCSGameMode.NONE)
            throw new RuntimeException("No gameMode was selected.");
        if(gameMode == IMCSGameMode.ACCEPT && gameId == null)
            throw new RuntimeException("Accepting gameId and player has not been set.");
        if(gameMode == IMCSGameMode.OFFER && player == Player.NONE)
            throw new RuntimeException("Own player for game offering has not been set.");

        try {
            if(gameMode == IMCSGameMode.OFFER) {
                imcs.changePassword("31337");
                imcs.offerGameAndWait(player.toString().charAt(0));
            } else {
                if(player != Player.NONE)
                    imcs.accept(gameId, player.toString().charAt(0));
                else
                    imcs.accept(gameId);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Move getMove(Board board) {
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
