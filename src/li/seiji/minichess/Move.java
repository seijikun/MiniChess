package li.seiji.minichess;

public class Move {

    public Square from;
    public Square to;

    public Move(Square from, Square to) {
        this.from = from;
        this.to = to;
    }

    public Move(String move) {
        this.from = new Square(move.substring(0, 2));
        this.from = new Square(move.substring(3, 5));
    }

    @Override
    public String toString() {
        return from.toString() + "-" + to.toString();
    }
}
