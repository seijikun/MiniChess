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
        if(state.gameState.isDefinitiveWin() && state.gameState.ordinal() != state.turn.ordinal())
            return -Float.MAX_VALUE / (float) state.turnCounter;

        float score = 0.0f;

        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                char fieldValue = state.board[y][x];
                char identifier = Square.getIdentifier(state.board, x, y);

                Piece p = new Piece(identifier, fieldValue, Player.parseIdentifier(fieldValue), x, y);
                if(state.turn == Player.parseIdentifier(fieldValue))
                    score += getScoreFromPieceOptimized(state, p);
                else
                    score -= getScoreFromPieceOptimized(state, p);
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

    private static float getScoreFromPieceOptimized(State state, Piece p) {
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
                return calculatePawnScore(state, p);
            default:
                return 0.0f;
        }
    }

    private static float calculateBishopScore(State state, Piece b) {
        float score = Bishop.pointScore;



        return score;
    }

    private static float calculatePawnScore(State state, Piece p) {
        float score = Pawn.pointScore;

        int sign = (Player.parseIdentifier(p.fieldValue) == Player.BLACK) ? 1 : -1;

        // If Pawn is at the opposite and of the field and can be promoted to a Queen
        if(p.player == Player.BLACK && p.y == Board.ROWS-1 || p.player == Player.WHITE && p.y == 1) {
            score += Queen.pointScore/3.0f;
        }
        else {
            //Is Pawn blocked?
            if(state.board[p.y+sign][p.x] != '.')
                score -= .3f;

            //Can Pawn capture to the left?
            if(p.x != 0) {
                char pieceLeft = state.board[p.y+sign][p.x-1];
                if(pieceLeft != '.' && Player.parseIdentifier(pieceLeft) != p.player)
                    score += .9f;
            }
            //Can Pawn capture to the right?
            if(p.x != Board.COLUMNS-1) {
                char pieceRight = state.board[p.y+sign][p.x+1];
                if(pieceRight != '.' && Player.parseIdentifier(pieceRight) != p.player)
                    score += .9f;
            }
        }
        return score;
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
