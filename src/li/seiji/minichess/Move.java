package li.seiji.minichess;

import li.seiji.minichess.squares.Square;

/**
 * Created by seiji on 5/15/17.
 */
public class Move {

    public Square from;
    public Square to;

    public Move(Square from, Square to){
        this.from = from;
        this.to = to;
    }

}