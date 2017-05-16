package li.seiji.helpers;

import li.seiji.minichess.Board;
import li.seiji.minichess.Player;
import li.seiji.minichess.Square;
import li.seiji.minichess.move.Move;

import java.util.HashSet;
import java.util.Random;

public class MoveHelper {

    private static Random rand = new Random();

    public static Square selectRandomFigure(Board state) {
        Square sqr = new Square(0, 0);
        do {
            sqr.x = rand.nextInt(Board.COLUMNS - 1);
            sqr.y = rand.nextInt(Board.ROWS - 1);
        } while(sqr.getIdentifier(state) == '.');
        return sqr;
    }

    public static Square selectRandomEmptyField(Board state) {
        Square sqr = new Square(0, 0);
        do {
            sqr.x = rand.nextInt(Board.COLUMNS - 1);
            sqr.y = rand.nextInt(Board.ROWS - 1);
        } while(sqr.getIdentifier(state) != '.');
        return sqr;
    }

    public static Move generateRandomPhysicallyValidMove(Board state) {
        Square from = selectRandomFigure(state);
        Square to = selectRandomEmptyField(state);
        return new Move(from, to);
    }

    public static Move generateRandomPhysicallyInvalidMove(Board state) {
        Square from = null; Square to = null;
        do {
            from = selectRandomFigure(state);
            to = selectRandomFigure(state); //search two squares that contain figures of the same team
        } while(Player.parseIdentifier(to.getIdentifier(state)) != state.turn);

        return new Move(from, to);
    }

    public static HashSet<Move> generateAllPhysicallyPossibleMoves(Board state, Square from) {
        HashSet<Move> result = new HashSet<>();

        for(int y = 0; y < Board.ROWS; ++y) {
            for(int x = 0; x < Board.COLUMNS; ++x) {
                if(from.x != x && from.y != y)
                    result.add(new Move(from, new Square(x, y)));
            }
        }
        return result;
    }

}
