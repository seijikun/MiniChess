package li.seiji;

import li.seiji.minichess.*;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.move.MoveGenerator;
import li.seiji.minichess.move.MoveValidator;

import java.util.List;

public class Main {

    public static void main(String[] args) throws InvalidMoveException {
        State state = new State();
        state.initialize();
        List<Move> moves = Board.getPossibleMoves(state);
        Board.prettyPrint(state);
        moves.stream().forEach(move -> System.out.println(move.toString()));
    }
}
