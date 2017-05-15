package li.seiji;

import li.seiji.minichess.*;
import li.seiji.minichess.move.Move;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        State state = new State();
        state.initialize();
        List<Move> moves = Board.getPossibleMoves(state);
        Board.prettyPrint(state);
        moves.stream().forEach(move -> System.out.println(move.toString()));
    }
}
