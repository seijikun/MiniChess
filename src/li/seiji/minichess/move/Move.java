package li.seiji.minichess.move;

import com.sun.media.sound.InvalidFormatException;
import li.seiji.minichess.Square;
import li.seiji.minichess.board.GameState;

public class Move {

    public Square from;
    public Square to;

    /* MOVE / UNMOVE */
    public char fromOldVal = 0;
    public char toOldVal = 0;
    public GameState oldGameState = GameState.ONGOING;

    public Move(Square from, Square to) {
        this.from = from;
        this.to = to;
    }

    public Move(String move) throws InvalidFormatException {
        this.from = new Square(move.substring(0, 2));
        this.to = new Square(move.substring(3, 5));
    }

    @Override
    public String toString() {
        return from.toString() + "-" + to.toString();
    }

    @Override
    public int hashCode() {
        return from.hashCode() ^ to.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Move o = (Move) obj;
        return (o.to.equals(to) && o.from.equals(from));
    }
}
