package li.seiji.minichess.figure;

import li.seiji.minichess.Board;
import li.seiji.minichess.Move;
import li.seiji.minichess.State;
import li.seiji.minichess.*;

import java.util.List;

public interface IFigure {

    static boolean isMoveValid(State state, Move move) { return false; }

    static boolean isMoveWithinBounds(State state, Move move) {
        return (move.to.x >= 0 && move.to.x < Board.COLUMNS && move.to.y >= 0 && move.to.y < Board.ROWS);
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
