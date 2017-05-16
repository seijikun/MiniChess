package li.seiji.minichess.player;

import li.seiji.minichess.Square;
import li.seiji.minichess.State;
import li.seiji.minichess.move.Move;

public interface IPlayer {
    Move getMove(State state);
}
