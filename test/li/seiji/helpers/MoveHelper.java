package li.seiji.helpers;

import li.seiji.minichess.Board;
import li.seiji.minichess.Player;
import li.seiji.minichess.Square;
import li.seiji.minichess.State;
import li.seiji.minichess.move.Move;

import java.util.Random;

public class MoveHelper {

    private static Random rand = new Random();

    public static Square selectRandomFigure(State state) {
        Square sqr = new Square(0, 0);
        do {
            sqr.x = rand.nextInt(Board.COLUMNS - 1);
            sqr.y = rand.nextInt(Board.ROWS - 1);
        } while(sqr.getIdentifier(state) == '.');
        return sqr;
    }

    public static Square selectRandomEmptyField(State state) {
        Square sqr = new Square(0, 0);
        do {
            sqr.x = rand.nextInt(Board.COLUMNS - 1);
            sqr.y = rand.nextInt(Board.ROWS - 1);
        } while(sqr.getIdentifier(state) != '.');
        return sqr;
    }

    public static Move generateRandomPhysicallyValidMove(State state) {
        Square from = selectRandomFigure(state);
        Square to = selectRandomEmptyField(state);
        return new Move(from, to);
    }

    public static Move generateRandomPhysicallyInvalidMove(State state) {
        Square from = null; Square to = null;
        do {
            from = selectRandomFigure(state);
            to = selectRandomFigure(state); //search two squares that contain figures of the same team
        } while(Player.parseIdentifier(from.getIdentifier(state)) != Player.parseIdentifier(to.getIdentifier(state)));

        return new Move(from, to);
    }

}
