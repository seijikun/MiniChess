package li.seiji.minichess.player;

import li.seiji.minichess.Square;
import li.seiji.minichess.State;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.move.MoveValidator;

import java.util.Scanner;

public class HumanPlayer implements Player {
    @Override
    public Move getMove(State state, Square from) {
        Move move;

        Scanner reader = new Scanner(System.in);

        do {
            String moveString = reader.nextLine();
            move = new Move(moveString);
        } while (!MoveValidator.isMoveValid(state, move));

        reader.close();

        return move;
    }
}
