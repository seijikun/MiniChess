package li.seiji;

import li.seiji.minichess.Board;
import li.seiji.minichess.InvalidMoveException;
import li.seiji.minichess.move.Move;

import java.util.List;

public class Main {

    public static void main(String[] args) throws InvalidMoveException {
        Board state = new Board();
        state.initialize();
        List<Move> moves = state.getPossibleMoves();
        state.prettyPrint();
        moves.stream().forEach(move -> System.out.println(move.toString()));
    }
}
