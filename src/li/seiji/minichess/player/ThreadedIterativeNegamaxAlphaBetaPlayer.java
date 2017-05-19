package li.seiji.minichess.player;

import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.FutureIteratedMove;
import li.seiji.minichess.move.FutureMove;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.util.ThreadPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class ThreadedIterativeNegamaxAlphaBetaPlayer extends PlayerBase {

    private ThreadPool threadPool = new ThreadPool();
    private int maxDepth;

    //move select optimization
    private int lastCapture = 0;

    public ThreadedIterativeNegamaxAlphaBetaPlayer(int maxDepth) {
        if(maxDepth < 2)
            throw new RuntimeException("MaxDepth of at least 2 required!");
        this.maxDepth = maxDepth;
    }



    @Override
    public void start(Player color) {
        threadPool.start();
    }

    @Override
    public Move getMove(Board board) throws InvalidMoveException {
        List<Future<FutureIteratedMove>> results = new ArrayList<>();

        abort = false;

        for(int i = 5; i <= maxDepth; ++i) {
            try {
                results.add(threadPool.addTask( new NegamaxTask(board.state.clone(), i) ));
            } catch (InterruptedException e) {}
        }

        try {
            Thread.sleep(7100);
            abort = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return findBestMove(results);
    }


    private Move findBestMove(List<Future<FutureIteratedMove>> results) {
        int deepestLevel = -1;
        Move bestMove = null;

        try {
            for(Future<FutureIteratedMove> moveFuture : results) {
                FutureIteratedMove move = moveFuture.get();
                if(move.completed && move.level > deepestLevel) {
                    deepestLevel = move.level;
                    bestMove = move.move;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Reached level: " + deepestLevel);
        return bestMove;
    }



    @Override
    public void setMove(Board board, Move move) {}

    @Override
    public void end() {
        //kill everything that is still alive
        threadPool.abort();
    }


    //Flag used by each thread to abort
    private volatile boolean abort = false;

    private class NegamaxTask implements Callable<FutureIteratedMove> {
        private State stateCopy;
        private int localMaxDepth;

        public NegamaxTask(State stateCopy, int depth) {
            this.stateCopy = stateCopy;
            this.localMaxDepth = depth;
        }

        @Override
        public FutureIteratedMove call() throws Exception {
            if(!abort) {
                try {
                    FutureMove move = negamax(stateCopy, this.localMaxDepth, -Float.MAX_VALUE, Float.MAX_VALUE, null);
                    return new FutureIteratedMove(localMaxDepth, move.move);
                } catch (InterruptedException e) {}
            }

            return FutureIteratedMove.EMPTY;
        }

        private FutureMove negamax(State state, int depth, float a, float b, Move parentMove) throws InvalidMoveException, InterruptedException {
            if(depth == 0 || state.gameState != GameState.ONGOING)
                return new FutureMove(null, evaluator.calculate(state, parentMove));

            if(abort)
                throw new InterruptedException();

            FutureMove bestMove = new FutureMove(null, Float.NEGATIVE_INFINITY);

            //ordering
            List<Move> possibleMoves = state.getPossibleMoves();
            Collections.shuffle(possibleMoves);

            for(Move possibleMove : possibleMoves) {
                state.move(possibleMove);
                FutureMove nextMove = negamax(state, depth - 1, -b, -a, possibleMove);
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

}
