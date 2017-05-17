package li.seiji.minichess.player;

import li.seiji.minichess.board.Board;
import li.seiji.minichess.move.Move;

public interface IPlayer {

    /**
     * Notifies the player that the game is about to start.
     */
    void start();

    /**
     * Called to get the players next move.
     * @param board Current state of the boad.
     * @return The new move of the player.
     */
    Move getMove(Board board);

    /**
     * This notifies the player about the other player's move.
     * @param board Current state of the board.
     * @param move Move of the other player.
     */
    void setMove(Board board, Move move);

}
