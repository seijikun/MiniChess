package li.seiji.minichess.player;

import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.Move;

import java.util.List;

public class NegamaxPlayer implements IPlayer {

    private int maxDepth = 0;

    public NegamaxPlayer(int maxDepth) {
        this.maxDepth = maxDepth;
    }


    @Override
    public void start(Player color) {}

    @Override
    public Move getMove(Board board) throws InvalidMoveException {
        negamax(board.state, maxDepth, true);
        return bestMove;
    }

    @Override
    public void setMove(Board board, Move move) {}

    @Override
    public void end() {}


    private Move bestMove = null;

    private float negamax(State state, int depth, boolean root) throws InvalidMoveException {
        if(depth == 0)
            return state.calculateScore();

        float bestValue = Float.NEGATIVE_INFINITY;
        List<Move> possibleMoves = state.getPossibleMoves();
        for(Move possibleMove : possibleMoves) {
            State subState = state.move(possibleMove);

            float score = -negamax(subState, depth - 1, false);

            if(score > bestValue) {
                bestValue = score;
                if(root) bestMove = possibleMove;
            }
        }
        return bestValue;
    }

}
