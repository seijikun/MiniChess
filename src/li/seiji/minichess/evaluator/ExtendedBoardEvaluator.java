package li.seiji.minichess.evaluator;

import li.seiji.minichess.Player;
import li.seiji.minichess.Square;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.State;
import li.seiji.minichess.figure.*;
import li.seiji.minichess.move.Move;

public class ExtendedBoardEvaluator implements IBoardEvaluator {

    @Override
    public float calculate(State state, Move move) {
        float score = 0.0f;

        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                char fieldValue = state.board[y][x];
                char identifier = Square.getIdentifier(state.board, x, y);

                Piece p = new Piece(identifier, fieldValue, Player.parseIdentifier(fieldValue), x, y);
                if(state.turn == Player.parseIdentifier(fieldValue))
                    score += getScoreFromPieceOptimized(p);
                else
                    score -= getScoreFromPieceOptimized(p);
            }
        }

        return score + processMove(state, move);
    }

    private float processMove(State state, Move move) {
        float score = 0.0f;
        if(move.to.getFieldValue(state) != '.') {
            score += IFigure.getScoreFromPiece(move.to.getIdentifier(state)) / 7.0f;
        }

        return score;
    }


    private static float calculatePawnScore(Piece p) {
        float score = Pawn.pointScore;

        int sign = (Player.parseIdentifier(p.fieldValue) == Player.BLACK) ? 1 : -1;

        if(p.player == Player.BLACK && p.y == Board.ROWS-1 || p.player == Player.WHITE && p.y == 1) {
            score += Queen.pointScore/3.0f;
        }


        return score;
    }


    private static float getScoreFromPieceOptimized(Piece p) {
        switch (p.identifier) {
            case King.identifier:
                return King.pointScore;
            case Queen.identifier:
                return Queen.pointScore;
            case Rook.identifier:
                return Rook.pointScore;
            case Bishop.identifier:
                return Bishop.pointScore;
            case Knight.identifier:
                return Knight.pointScore;
            case Pawn.identifier:
                return calculatePawnScore(p);
            default:
                return 0.0f;
        }
    }



    private static class Piece {
        char identifier;
        char fieldValue;
        Player player;
        int x,y;

        public Piece(char identifier, char fieldValue, Player player, int x, int y) {
            this.identifier = identifier;
            this.fieldValue = fieldValue;
            this.player = player;
            this.x = x;
            this.y = y;
        }
    }

}
