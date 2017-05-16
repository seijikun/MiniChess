package li.seiji.minichess.player;

import li.seiji.minichess.State;
import li.seiji.minichess.move.Move;

public class RandomPlayer implements IPlayer {
    @Override
    public Move getMove(State state) {
        //TODO: implement
        return null;
//        List<Move> currentMoves = new ArrayList<>();
//        MoveGenerator.moveList(state, currentMoves, from);
//
//        int index = ThreadLocalRandom.current().nextInt(0, currentMoves.size()-1);
//
//        return currentMoves.get(index);
    }
}
