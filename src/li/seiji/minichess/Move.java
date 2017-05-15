package li.seiji.minichess;

/**
 * Created by seiji on 5/15/17.
 */
public class Move {

    public Board.Square from;
    public Board.Square to;

    public Move(Board.Square from, Board.Square to){
        this.from = from;
        this.to = to;
    }

}
