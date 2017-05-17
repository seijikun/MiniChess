package li.seiji.minichess.player;

import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.FutureMove;
import li.seiji.minichess.move.Move;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NegamaxPlayer implements IPlayer {

    private int maxDepth = 0;

    public NegamaxPlayer(int maxDepth) {
        this.maxDepth = maxDepth;
    }


    @Override
    public void start(Player color) {}

    @Override
    public Move getMove(Board board) throws InvalidMoveException {
        FutureMove move = negamax(board.state, maxDepth, true);
        return move.move;
    }

    @Override
    public void setMove(Board board, Move move) {}

    @Override
    public void end() {}


    private FutureMove negamax(State state, int depth, boolean root) throws InvalidMoveException {
        if(depth == 0) return new FutureMove(null, state.calculateScore());

        FutureMove move = new FutureMove(null, Float.NEGATIVE_INFINITY);
        for(Move possibleMove : state.getPossibleMoves()) {
            State subState = state.move(possibleMove);

            if(subState.gameState != GameState.ONGOING) {
                return new FutureMove(possibleMove, -1.0f * subState.calculateScore());
            }

            FutureMove next = negamax(subState, depth - 1, false);
            float score = (-1) * next.value;

            if(score > move.value || (score == move.value && ThreadLocalRandom.current().nextInt(3) == 0)) {
                move.move = possibleMove;
                move.value = score;
            }
        }
        return move;
    }

}
