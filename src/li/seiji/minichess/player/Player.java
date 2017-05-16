package li.seiji.minichess.player;

import li.seiji.minichess.Square;
import li.seiji.minichess.State;
import li.seiji.minichess.move.Move;

/**
 * Created by Lyot on 16.05.2017.
 */
public interface Player {
    Move getMove(State state, Square from);
}
