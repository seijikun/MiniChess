package li.seiji.minichess.player;

import li.seiji.minichess.Board;
import li.seiji.minichess.State;
import li.seiji.minichess.move.Move;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPlayer implements IPlayer {
    @Override
    public Move getMove(State state) {
        List<Move> currentMoves = Board.getPossibleMoves(state);

        int index = ThreadLocalRandom.current().nextInt(0, currentMoves.size()-1);

        return currentMoves.get(index);
    }
}
