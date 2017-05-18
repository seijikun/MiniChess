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

public class ThreadedNegamaxAlphaBetaPlayer implements IPlayer {

    private ExecutorService threadPool;
    private int maxDepth;

    public ThreadedNegamaxAlphaBetaPlayer(int maxDepth) {
        this.maxDepth = maxDepth;
    }



    @Override
    public void start(Player color) {
        threadPool = Executors.newSingleThreadExecutor();
    }

    @Override
    public Move getMove(Board board) throws InvalidMoveException {
        List<Move> possibleMoves = board.getPossibleMoves();
        List<NegamaxTask> tasks = new ArrayList<>();

        for(Move possibleMove : possibleMoves) {
            State stateCopy = board.state.clone();
            tasks.add(new NegamaxTask(stateCopy, possibleMove));
        }

        List<Future<FutureMove>> results = new ArrayList<>();
        try {
            results = threadPool.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return findBestMove(results);
    }


    private Move findBestMove(List<Future<FutureMove>> results) {
        float bestScore = Float.NEGATIVE_INFINITY;
        Move bestMove = null;

        try {
            for(Future<FutureMove> move : results) {
                float newScore = move.get().value;
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

        public NegamaxTask(State stateCopy, Move rootMove) {
            this.stateCopy = stateCopy;
            this.rootMove = rootMove;
        }

        @Override
        public FutureMove call() throws Exception {
            stateCopy.move(rootMove);
            if(stateCopy.gameState != GameState.ONGOING)
                return new FutureMove(rootMove, stateCopy.calculateScore());

            FutureMove result = new FutureMove(
                    rootMove,
                    negamax(stateCopy, maxDepth - 1, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY).value
            );
            return result;
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

}
