package li.seiji.minichess.player;

import li.seiji.minichess.Board;
import li.seiji.minichess.move.Move;

public interface IPlayer {

    /**
     * Notifies the player that the game is about to start.
     */
    void start();

    /**
     * Called to get the players next move.
     * @param state Current state of the boad.
     * @return The new move of the player.
     */
    Move getMove(Board state);

    /**
     * This notifies the player about the other player's move.
     * @param state Current state of the board.
     * @param move Move of the other player.
     */
    void setMove(Board state, Move move);

}
