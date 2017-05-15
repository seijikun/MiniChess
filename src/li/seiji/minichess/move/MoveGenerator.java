package li.seiji.minichess.move;

import li.seiji.minichess.*;
import li.seiji.minichess.figure.*;

import java.util.List;

public class MoveGenerator {

    public static void moveList(State state, List<Move> result, Square from) {
        char identifier = Character.toLowerCase(state.board[from.y][from.x]);
        boolean stopShort;

        switch (identifier) {
            case Queen.identifier:
            case King.identifier:
                stopShort = (identifier == King.identifier);
                symmScan(state, result, from, 0, 1, stopShort);
                symmScan(state, result, from, 1, 1, stopShort);
                break;
            case Bishop.identifier:
            case Rook.identifier:
                stopShort = (identifier == Bishop.identifier);
                Capture capture = (identifier == Rook.identifier) ? Capture.TRUE : Capture.FALSE;
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
                int dir = (IFigure.getFieldPlayer(state, from) == Player.BLACK) ? 1 : -1;

                scan(state, result, from, -1, dir, true, Capture.ONLY);
                scan(state, result, from, 1, dir, true, Capture.ONLY);
                scan(state, result, from, 0, dir, true, Capture.FALSE);
        }
    }



    private static void scan(State state, List<Move> result, Square from, int dx, int dy, boolean stopShort, Capture capture) {
        int x = from.x, y = from.y;
        Player player = IFigure.getFieldPlayer(state, from);

        do {
            x += dx; y +=dy;

            if(x < 0 || x >= Board.COLUMNS || y < 0 || y >= Board.ROWS)
                break;

            if(state.board[y][x] != '.') {
                if(Player.parseIdentifier(state.board[y][x]) == player)
                    break;
                if(capture == Capture.FALSE)
                    break;
                stopShort = true;
            }
            else if(capture == Capture.ONLY)
                break;
            result.add(new Move(from, new Square(x, y)));
        } while(!stopShort);
    }

    private static void symmScan(State state, List<Move> result, Square from, int dx, int dy, boolean stopShort, Capture capture) {
        for(int i = 0; i < 4; i++) {
            scan(state, result, from, dx, dy, stopShort, capture);

            //swap dx and dy
            int tmp = dx; dx = dy; dy = tmp;

            dy *= -1;
        }
    }

    private static void symmScan(State state, List<Move> result, Square from, int dx, int dy, boolean stopShort) {
        symmScan(state, result, from, dx, dy, stopShort, Capture.TRUE);
    }

}
