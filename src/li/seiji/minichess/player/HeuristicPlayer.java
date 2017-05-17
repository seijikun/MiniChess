package li.seiji.minichess.player;

import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.Move;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HeuristicPlayer implements IPlayer {

    @Override
    public void start(Player color) {}

    @Override
    public Move getMove(Board board) throws InvalidMoveException {
        List<Move> possibleMoves = board.getPossibleMoves();
        Move bestMove = null;
        float bestScore = -Float.MAX_VALUE;
        for(Move possibleMove : possibleMoves) {

            State moveResult = board.state.move(possibleMove);
            float score = -moveResult.calculateScore();

            if(score > bestScore || (score == bestScore && ThreadLocalRandom.current().nextBoolean())) {
                bestMove = possibleMove;
                bestScore = score;
            }
        }

        return bestMove;
    }

    @Override
    public void setMove(Board state, Move move) {
    }

    @Override
    public void end() {

    }

}
