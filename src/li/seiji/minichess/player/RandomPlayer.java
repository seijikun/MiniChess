package li.seiji.minichess.player;

import li.seiji.minichess.board.Board;
import li.seiji.minichess.move.Move;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPlayer implements IPlayer {

    @Override
    public void start() {

    }

    @Override
    public Move getMove(Board state) {
        List<Move> currentMoves = state.getPossibleMoves();

        int index = ThreadLocalRandom.current().nextInt(0, currentMoves.size() - 1);

        return currentMoves.get(index);
    }

    @Override
    public void setMove(Board state, Move move) {

    }
}
