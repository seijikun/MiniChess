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

    //delta
    static int getMoveDeltaX(Move move) {
        return (move.to.x - move.from.x);
    }
    static int getMoveDeltaY(Move move) {
        return (move.to.y - move.from.y);
    }
    static int getAbsMoveDeltaX(Move move) {
        return Math.abs(getMoveDeltaX(move));
    }
    static int getAbsMoveDeltaY(Move move) {
        return Math.abs(getMoveDeltaY(move));
    }





    static boolean isStraightMove(Move move) {
        return (move.from.x == move.to.x && move.from.y != move.to.y) ||
                (move.from.x != move.to.x && move.from.y == move.to.y);
    }

    static int getStraightMoveLen(Move move) {
        if(move.from.x == move.to.x) //y changed
            return getAbsMoveDeltaY(move);
        else //x changed
            return getAbsMoveDeltaX(move);
    }

    static boolean isDiagonalMove(Move move) {
        return getAbsMoveDeltaX(move) == getAbsMoveDeltaY(move);
    }

    static int getDiagonalMoveLen(Move move) {
        return getAbsMoveDeltaX(move);
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
