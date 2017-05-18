package li.seiji.minichess.player;

import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.FutureMove;
import li.seiji.minichess.move.Move;


public class NegamaxAlphaBetaPlayer implements IPlayer {

    private int maxDepth;

    public NegamaxAlphaBetaPlayer(int maxDepth) {
        this.maxDepth = maxDepth;
    }


    @Override
    public void start(Player color) {

    }

    @Override
    public Move getMove(Board board) throws InvalidMoveException {
        FutureMove move = negamax(board.state, maxDepth, -Float.MAX_VALUE, Float.MAX_VALUE);
        return move.move;
    }

    @Override
    public void setMove(Board board, Move move) {

    }

    @Override
    public void end() {

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


//    private FutureMove negamax(State state, int depth, float a, float b) throws InvalidMoveException {
//        if(depth == 0) return new FutureMove(null, state.calculateScore());
//
//        FutureMove bestMove = new FutureMove(null, Float.NEGATIVE_INFINITY);
//        for(Move possibleMove : state.getPossibleMoves()) {
//            State subState = state.move(possibleMove);
//
//            float score;
//            if(subState.gameState != GameState.ONGOING) {
//                score = subState.calculateScore();
//            } else {
//                FutureMove next = negamax(subState, depth - 1, -b, -a);
//                score = (-1) * next.value;
//            }
//
//            if(score >= b)
//                return new FutureMove(possibleMove, score);
//            if(score > a)
//                a = score;
//            if(score > bestMove.value) {
//                bestMove.move = possibleMove;
//                bestMove.value = score;
//            }
//        }
//        return bestMove;
//    }

}
