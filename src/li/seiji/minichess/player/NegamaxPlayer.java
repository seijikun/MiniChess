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
        FutureMove move = negamax(board.state, maxDepth);
        return move.move;
    }

    @Override
    public void setMove(Board board, Move move) {}

    @Override
    public void end() {}


    private FutureMove negamax(State state, int depth) throws InvalidMoveException {
        if(depth == 0)
            return new FutureMove(null, state.calculateScore());

        FutureMove move = new FutureMove(null, Float.NEGATIVE_INFINITY);
        for(Move possibleMove : state.getPossibleMoves()) {
            state.move(possibleMove);
            float subScore = state.calculateScore();

            if(state.gameState != GameState.ONGOING) {
                state.unmove(possibleMove);
                return new FutureMove(possibleMove, -1.0f * subScore);
            }

            FutureMove next = negamax(state, depth - 1);
            float score = (-1) * next.value;
            state.unmove(possibleMove);

            if(score > move.value || (score == move.value && ThreadLocalRandom.current().nextInt(3) == 0)) {
                move.move = possibleMove;
                move.value = score;
            }
        }
        return move;
    }

}
