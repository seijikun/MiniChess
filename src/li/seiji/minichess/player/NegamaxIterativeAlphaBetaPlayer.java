package li.seiji.minichess.player;

import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.FutureMove;
import li.seiji.minichess.move.Move;


public class NegamaxIterativeAlphaBetaPlayer extends PlayerBase {

    private int maxDepth;
    private Player color;

    public NegamaxIterativeAlphaBetaPlayer(int maxDepth) {
        this.maxDepth = maxDepth;
    }


    @Override
    public void start(Player color) {
        this.color = color;
    }

    @Override
    public Move getMove(Board board) throws InvalidMoveException {
        FutureMove move = negamaxIterative(board.state, 6000);
        return move.move;
    }

    @Override
    public void setMove(Board board, Move move) {

    }

    @Override
    public void end() {

    }

    private FutureMove negamaxIterative(State state, int timeLimit) throws InvalidMoveException {
        int depth = 2;

        FutureMove bestMove = negamax(state, 1, -Float.MAX_VALUE, Float.MAX_VALUE);
        long startTime = System.currentTimeMillis();

        while(true) {
            FutureMove possibleMove = negamax(state, depth, -Float.MAX_VALUE, Float.MAX_VALUE);

            if((System.currentTimeMillis() - startTime >= timeLimit))
                return bestMove;

            state.move(possibleMove.move);
            if(state.gameState == GameState.TIE || state.gameState.ordinal() == color.ordinal()) {
                state.unmove(possibleMove.move);
                return possibleMove;
            }
            if(state.gameState.ordinal() != color.ordinal() && state.gameState != GameState.ONGOING) {
                state.unmove(possibleMove.move);
                return bestMove;
            }

            bestMove = possibleMove;
            depth++;
            state.unmove(possibleMove.move);
        }
    }

    private FutureMove negamax(State state, int depth, float a, float b) throws InvalidMoveException {
        if(depth == 0 || state.gameState != GameState.ONGOING)
            return new FutureMove(null, state.calculateScore());

        FutureMove bestMove = new FutureMove(null, Float.NEGATIVE_INFINITY);
        for(Move possibleMove : state.getPossibleMoves()) {
            state.move(possibleMove);
            FutureMove nextMove = negamax(state, depth - 1, -b, -a);
            state.unmove(possibleMove);
            float score = (-1) * nextMove.value;

            if(score > bestMove.value) {
                bestMove.value = score;
                bestMove.move = possibleMove;
            }
            a = Math.max(a, score);
            if(a >= b)
                break;
        }
        return bestMove;
    }
}
