package li.seiji.minichess.evaluator;

import li.seiji.minichess.Player;
import li.seiji.minichess.Square;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.State;
import li.seiji.minichess.figure.*;
import li.seiji.minichess.move.Move;

public class DefaultBoardEvaluator implements IBoardEvaluator {

    @Override
    public float calculate(State state, Move move) {
        float score = 0.0f;
        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                char fieldValue = state.board[y][x];
                char identifier = Square.getIdentifier(state.board, x, y);
                if(state.turn == Player.parseIdentifier(fieldValue))
                    score += IFigure.getScoreFromPiece(identifier);
                else
                    score -= IFigure.getScoreFromPiece(identifier);
            }
        }

        return score;
    }

}
