package li.seiji.minichess.player;

import li.seiji.minichess.Board;
import li.seiji.minichess.move.Move;

public interface IPlayer {
    Move getMove(Board state);
}
