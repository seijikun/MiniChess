package li.seiji.minichess.figure;

import li.seiji.minichess.*;

import java.util.List;
import li.seiji.minichess.Board;
import li.seiji.minichess.Move;
import li.seiji.minichess.State;

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

    static void scan(State state, List<Move> result, Square from, int dx, int dy, boolean stopShort, Capture capture) {
        int x = from.x, y = from.y;
        Player player = Player.parseIdentifier(state.board[x][y]);

        do {
            x += dx; y +=dy;

            if((x < 0 || x > Board.COLUMNS) || (y < 0 || y > Board.ROWS))
                break;

            if(state.board[x][y] != '.') {
                if(Player.parseIdentifier(state.board[x][y]) == player)
                    break;
                if(capture == Capture.FALSE)
                    break;
                stopShort = true;
            }
            else if(capture == Capture.ONLY)
                break;
            result.add(new Move(from, new Square(x, y)));
        } while(stopShort);
    }

    static void symmScan(State state, List<Move> result, Square from, int dx, int dy, boolean stopShort, Capture capture) {
        for(int i = 0; i < 4; i++) {
            scan(state, result, from, dx, dy, stopShort, capture);
            dx = dy;
            dy *= -1;
        }
    }

    static void symmScan(State state, List<Move> result, Square from, int dx, int dy, boolean stopShort) {
        symmScan(state, result, from, dx, dy, stopShort, Capture.TRUE);
    }

    static void moveList(State state, List<Move> result, Square from) {
        char identifier = state.board[from.y][from.x];
        boolean stopShort;

        switch (identifier) {
            case Queen.identifier:
            case King.identifier:
                stopShort = identifier == King.identifier;
                symmScan(state, result, from, 0, 1, stopShort);
                symmScan(state, result, from, 1, 1, stopShort);
                break;
            case Bishop.identifier:
            case Rook.identifier:
                stopShort = identifier == Bishop.identifier;
                Capture capture = identifier == Rook.identifier ? Capture.TRUE : Capture.FALSE;
                symmScan(state, result, from, 0, 1, stopShort, capture);
                if(identifier == Bishop.identifier) {
                    stopShort = false;
                    capture = Capture.TRUE;
                    symmScan(state, result, from, 1, 1, stopShort, capture);
                }
                break;
            case Knight.identifier:
                symmScan(state, result, from, 1, 2, true);
                symmScan(state, result, from, -1, 2, true);
                break;
            case Pawn.identifier:
                int dir = Player.parseIdentifier(identifier) == Player.BLACK ? -1 : 1;

                scan(state, result, from, -1, dir, true, Capture.ONLY);
                scan(state, result, from, 1, dir, true, Capture.ONLY);
                scan(state, result, from, 0, dir, true, Capture.FALSE);
        }

    }
}
