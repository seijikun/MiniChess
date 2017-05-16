package li.seiji.minichess.player;

import com.sun.media.sound.InvalidFormatException;
import li.seiji.minichess.Board;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.move.MoveValidator;

import java.util.Scanner;

public class HumanPlayer implements IPlayer {

    public void start() {}

    @Override
    public Move getMove(Board state) {
        Move move = null;
        Scanner reader = new Scanner(System.in);

        do {
            String moveString = reader.nextLine();
            try {
                move = new Move(moveString);
            } catch (InvalidFormatException e) {
                System.out.println("Not a valid move string");
            }
        } while (move == null || !MoveValidator.isMoveValid(state, move));

        reader.close();
        return move;
    }

    @Override
    public void setMove(Board state, Move move) {}

}
