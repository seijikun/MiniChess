package li.seiji.minichess.move;

public class FutureMove {
    public Move move;
    public float value;
    public FutureMove(Move move, float value) {
        this.move = move;
        this.value = value;
    }
}