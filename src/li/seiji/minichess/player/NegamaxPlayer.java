package li.seiji.minichess.player;

import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.Move;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class NegamaxPlayer implements IPlayer {

    private ExecutorService threadPool = Executors.newSingleThreadExecutor();

    private Player color;
    private GameState victoryGameState;

    @Override
    public void start(Player color) {
        this.color = color;
        victoryGameState = (color == Player.BLACK) ? GameState.WIN_BLACK : GameState.WIN_WHITE;
    }

    @Override
    public Move getMove(Board board) throws InvalidMoveException {
        boolean shouldStop = false;
        List<Move> possibleMoves = board.getPossibleMoves();

        List<NegamaxTask> calculatorTasks = new ArrayList<>();
        for(Move possibleMove : possibleMoves) {
            State state = board.state.move(possibleMove);
            if(state.gameState == victoryGameState)
                return possibleMove; //already found a winning move, exit
            calculatorTasks.add(new NegamaxTask(state, 1));
        }

        //execute
        List<Future<Float>> results = new ArrayList<>();
        try {
            results = threadPool.invokeAll(calculatorTasks);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return findBestMove(results, possibleMoves);
    }

    @Override
    public void setMove(Board board, Move move) {

    }



    private Move findBestMove(List<Future<Float>> results, List<Move> moves) {
        float bestScore = -Float.MAX_VALUE;
        Move bestMove = null;

        try {
            for(int i = 0; i < moves.size(); ++i) {
                float newScore = results.get(i).get();
                if(newScore > bestScore) {
                    bestScore = newScore;
                    bestMove = moves.get(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bestMove;
    }


    public class NegamaxTask implements Callable<Float> {

        private State state;
        private int maxDepth;
        private Reference<Boolean> shouldStop;

        public NegamaxTask(State state, int maxDepth) {
            this.state = state;
            this.maxDepth = maxDepth;
            //this.shouldStop = shouldStop;
        }

        @Override
        public Float call() throws Exception {
            return negamax(state, maxDepth);
        }


        private float negamax(State state, int depth) throws InvalidMoveException {
            if(depth == 0) return state.calculateScore();

            List<Move> possibleMoves = state.getPossibleMoves();
            Move bestMove = null;
            float bestScore = -Float.MAX_VALUE;
            for (Move possibleMove : possibleMoves) {
                State moveResult = state.move(possibleMove);
                if(moveResult.gameState == victoryGameState)
                    return moveResult.calculateScore();
                //TODO: implement recursion
            }

            return bestScore;
        }

    };

}
