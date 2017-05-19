package li.seiji.minichess.player;

import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.move.Move;

public abstract class PlayerBase {

    /**
     * Notifies the player that the game is about to start.
     * @param color The color this player plays with.
     */
    public abstract void start(Player color);

    /**
     * Called to get the players next move.
     * @param board Current state of the boad.
     * @return The new move of the player.
     */
    public abstract Move getMove(Board board) throws InvalidMoveException;

    /**
     * This notifies the player about the other player's move.
     * @param board Current state of the board.
     * @param move Move of the other player.
     */
    public abstract void setMove(Board board, Move move);

    /**
     * Notifies the player that the game has ended.
     */
    public abstract void end();

}
