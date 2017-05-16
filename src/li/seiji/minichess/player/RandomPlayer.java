package li.seiji.minichess.player;

import li.seiji.minichess.Square;
import li.seiji.minichess.State;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.move.MoveGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPlayer implements Player {
    @Override
    public Move getMove(State state, Square from) {
        List<Move> currentMoves = new ArrayList<>();
        MoveGenerator.moveList(state, currentMoves, from);

        int index = ThreadLocalRandom.current().nextInt(0, currentMoves.size()-1);

        return currentMoves.get(index);
    }
}
