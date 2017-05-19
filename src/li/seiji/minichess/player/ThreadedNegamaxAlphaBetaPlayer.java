package li.seiji.minichess.player;

import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.FutureMove;
import li.seiji.minichess.move.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadedNegamaxAlphaBetaPlayer extends PlayerBase {

    private ExecutorService threadPool;
    private int maxDepth;

    public ThreadedNegamaxAlphaBetaPlayer(int maxDepth) {
        this.maxDepth = maxDepth;
    }



    @Override
    public void start(Player color) {
        threadPool = Executors.newFixedThreadPool(4);
    }

    @Override
    public Move getMove(Board board) throws InvalidMoveException {
        List<Future<FutureMove>> results = new ArrayList<>();

        for(Move possibleMove : board.state.getPossibleMoves()) {
            results.add(threadPool.submit(
                new NegamaxTask(possibleMove, board.state.clone(), maxDepth - 1, -Float.MAX_VALUE, Float.MAX_VALUE)
            ));
        }

        return findBestMove(results);
    }


    private Move findBestMove(List<Future<FutureMove>> results) {
        float bestScore = Float.NEGATIVE_INFINITY;
        Move bestMove = null;

        try {
            for(Future<FutureMove> move : results) {
                float newScore = (-1) * move.get().value;
                if(newScore > bestScore || (newScore == bestScore && ThreadLocalRandom.current().nextBoolean())) {
                    bestScore = newScore;
                    bestMove = move.get().move;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bestMove;
    }



    @Override
    public void setMove(Board board, Move move) {}

    @Override
    public void end() {
        threadPool.shutdown();
    }



    private class NegamaxTask implements Callable<FutureMove> {
        private State stateCopy;
        private Move rootMove;
        private int depth;
        private float a;
        private float b;

        public NegamaxTask(Move rootMove, State stateCopy, int depth, float a, float b) {
            this.stateCopy = stateCopy;
            this.rootMove = rootMove;
            this.depth = depth;
            this.a = a;
            this.b = b;
        }

        @Override
        public FutureMove call() throws Exception {
            stateCopy.move(rootMove);
            if(stateCopy.gameState != GameState.ONGOING)
                return new FutureMove(rootMove, evaluator.calculate(stateCopy, rootMove));

            return new FutureMove(
                    rootMove,
                    negamax(stateCopy, depth, a, b, rootMove).value
            );
        }

        private FutureMove negamax(State state, int depth, float a, float b, Move parentMove) throws InvalidMoveException {
            if(depth == 0 || state.gameState != GameState.ONGOING)
                return new FutureMove(null, evaluator.calculate(state, parentMove));

            FutureMove bestMove = new FutureMove(null, Float.NEGATIVE_INFINITY);
            for(Move possibleMove : state.getPossibleMoves()) {
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
