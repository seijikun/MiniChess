package li.seiji.minichess.move;

public class FutureIteratedMove {

    public int level = -1;
    public boolean completed = true;

    public Move move = null;


    public FutureIteratedMove(int level, Move move) {
        this.level = level;
        this.move = move;
    }





    private FutureIteratedMove(){
        completed = false;
    }

    public static final FutureIteratedMove EMPTY = new FutureIteratedMove();
}
