package li.seiji.minichess.player;

import com.sun.media.sound.InvalidFormatException;
import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.move.MoveValidator;

import java.util.Scanner;

public class HumanPlayer extends PlayerBase {

    Scanner scanner = null;

    public void start(Player color) {
        scanner = new Scanner(System.in);
    }

    @Override
    public Move getMove(Board board) {
        Move move = null;

        do {
            String moveString = scanner.nextLine();
            try {
                move = new Move(moveString);
            } catch (InvalidFormatException e) {
                System.out.println("Not a valid move string");
            }
        } while (move == null || !MoveValidator.isMoveValid(board.state, move));

        return move;
    }

    @Override
    public void setMove(Board board, Move move) {}

    @Override
    public void end() {

    }

}
