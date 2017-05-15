package li.seiji.minichess.figure;

import li.seiji.minichess.*;

public interface IFigure {

    /* INTERFACE */
    static boolean isMoveValid(State state, Move move) {
        if(!IFigure.isMoveWithinBounds(state, move)) return false;
        if(IFigure.getFieldPlayer(state, move.to) == state.turn) return false;
        return true;
    }



    /* HELPERS */
    static boolean isMoveWithinBounds(State state, Move move) {
        return (move.to.x >= 0 && move.to.x < Board.COLUMNS && move.to.y >= 0 && move.to.y < Board.ROWS);
    }

    static boolean isStraightMove(Move move) {
        return (move.from.x == move.to.x && move.from.y != move.to.y) ||
                (move.from.x != move.to.x && move.from.y == move.to.y);
    }

    static int getStraightMoveLen(Move move) {
        if(move.from.x == move.to.x) //y changed
            return Math.abs(move.to.y - move.from.y);
        else //x changed
            return Math.abs(move.to.x - move.from.x);
    }

    static boolean isDiagonalMove(Move move) {
        int dX = Math.abs(move.to.x - move.from.x);
        int dY = Math.abs(move.to.y - move.from.y);
        return (dX == dY);
    }

    static int getDiagonalMoveLen(Move move) {
        return Math.abs(move.to.x - move.from.x);
    }

    static Player getFieldPlayer(State state, Square dst) {
        return Player.parseIdentifier(dst.getIdentifier(state));
    }

    static boolean isDestinationFieldAnEnemy(State state, Move move, Player currentPlayer) {
        Player fieldPlayer = getFieldPlayer(state, move.to);
        if(currentPlayer == Player.BLACK)
            return (currentPlayer == Player.WHITE);
        else
            return (currentPlayer == Player.BLACK);
    }

}
