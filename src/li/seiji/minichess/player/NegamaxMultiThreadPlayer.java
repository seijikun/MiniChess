package li.seiji.minichess.player;

import com.sun.org.apache.bcel.internal.generic.FLOAD;
import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.FutureMove;
import li.seiji.minichess.move.Move;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class NegamaxMultiThreadPlayer implements IPlayer {

    private ExecutorService threadPool = null;

    private Player color;
    private GameState victoryGameState;



    @Override
    public void start(Player color) {
        threadPool = Executors.newSingleThreadExecutor();
        //threadPool = Executors.newFixedThreadPool(4);
        this.color = color;
        victoryGameState = (color == Player.BLACK) ? GameState.WIN_BLACK : GameState.WIN_WHITE;
    }

    @Override
    public Move getMove(Board board) throws InvalidMoveException {
        //boolean shouldStop = false;
        List<Move> possibleMoves = board.getPossibleMoves();

        List<NegamaxTask> calculatorTasks = new ArrayList<>();
        for(Move possibleMove : possibleMoves) {
            State state = board.state.move(possibleMove);
            if(state.gameState == victoryGameState)
                return possibleMove; //already found a winning move, exit
            calculatorTasks.add(new NegamaxTask(possibleMove, state, 4));
        }

        //execute
        List<Future<FutureMove>> results = new ArrayList<>();
        try {
            results = threadPool.invokeAll(calculatorTasks);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return findBestMove(results);
    }

    @Override
    public void setMove(Board board, Move move) {

    }

    @Override
    public void end() {
        threadPool.shutdown();
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


    public class NegamaxTask implements Callable<FutureMove> {

        private Move rootMove;
        private State state;
        private int maxDepth;
        private Reference<Boolean> shouldStop;

        public NegamaxTask(Move rootMove, State state, int maxDepth) {
            this.rootMove = rootMove;
            this.state = state;
            this.maxDepth = maxDepth;
            //this.shouldStop = shouldStop;
        }

        @Override
        public FutureMove call() throws Exception {
            return new FutureMove(rootMove, negamax(state, maxDepth));
        }


        private float negamax(State state, int depth) throws InvalidMoveException {
            if(depth == 0) return state.calculateScore();

            List<Move> possibleMoves = state.getPossibleMoves();
            float bestScore = Float.NEGATIVE_INFINITY;
            for (Move possibleMove : possibleMoves) {
                State moveResult = state.move(possibleMove);
                if(moveResult.gameState == victoryGameState)
                    return -moveResult.calculateScore();
                if(moveResult.gameState != GameState.ONGOING)
                    continue;

                float score = negamax(moveResult, depth - 1);
                if(score > bestScore || (score == bestScore && ThreadLocalRandom.current().nextBoolean()))
                    bestScore = score;
                else if(bestScore == Float.NEGATIVE_INFINITY) {
                    System.out.println();//TODO: Remove
                }
            }

            return bestScore;
        }

    };

}
