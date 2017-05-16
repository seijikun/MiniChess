package li.seiji.minichess.figure;

import li.seiji.minichess.Board;
import li.seiji.minichess.Player;
import li.seiji.minichess.Square;
import li.seiji.minichess.State;
import li.seiji.minichess.move.Move;

import java.util.function.Function;

public interface IFigure {

    /* HELPERS */
    static boolean isMoveWithinBounds(State state, Move move) {
        return (move.to.x >= 0 && move.to.x < Board.COLUMNS && move.to.y >= 0 && move.to.y < Board.ROWS);
    }
    static Player getFieldPlayer(State state, Square dst) {
        return Player.parseIdentifier(dst.getIdentifier(state));
    }

    //delta
    static int getMoveDirX(Move move) {
        if(move.from.x > move.to.x) return -1;
        if(move.from.x < move.to.x) return 1;
        return 0;
    }
    static int getMoveDirY(Move move) {
        if(move.from.y > move.to.y) return -1;
        if(move.from.y < move.to.y) return 1;
        return 0;
    }
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


    /* STRAIGHT */
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
    static boolean checkStraightIsBlocked(State state, Move move, Function<Player, Boolean> isBlockade) {
        int xDir = getMoveDirX(move);
        int yDir = getMoveDirY(move);

        for(int x = move.from.x + xDir; x != move.to.x; x += xDir) {
            Square square = new Square(x, move.to.y);
            if(isBlockade.apply(getFieldPlayer(state, square))) return true;
        }
        for(int y = move.from.y + yDir; y != move.to.y; y += yDir) {
            Square square = new Square(move.to.x, y);
            if(isBlockade.apply(getFieldPlayer(state, square))) return true;
        }
        return false;
    }

    /* DIAGONAL */
    static boolean isDiagonalMove(Move move) {
        return getAbsMoveDeltaX(move) == getAbsMoveDeltaY(move);
    }
    static int getDiagonalMoveLen(Move move) {
        return getAbsMoveDeltaX(move);
    }

    static boolean isDestinationFieldAnEnemy(State state, Move move, Player currentPlayer) {
        Player fieldPlayer = getFieldPlayer(state, move.to);
        if(currentPlayer == Player.BLACK)
            return (fieldPlayer == Player.WHITE);
        else
            return (fieldPlayer == Player.BLACK);
    }

    static boolean checkDiagonalIsBlocked(State state, Move move, Function<Player, Boolean> isBlockade) {
        int xDir = getMoveDirX(move);
        int yDir = getMoveDirY(move);

        for(int x = move.from.x + xDir, y = move.from.y + yDir; x != move.to.x; x += xDir, y += yDir) {
            Square square = new Square(x, y);
            if(isBlockade.apply(getFieldPlayer(state, square))) return true;
        }

        return false;
    }

}
