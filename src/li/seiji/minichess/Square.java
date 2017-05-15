package li.seiji.minichess;

public class Square {

    public int x;
    public int y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Square(String position) {
        x = (position.charAt(0) - 'a');
        y = (position.charAt(1) - '1');
    }

    public char getIdentifier(State state) {
        return state.board[y][x];
    }

}