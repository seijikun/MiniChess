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

    public void setIdentifier(State state, char identifier) { state.board[y][x] = identifier; }

    @Override
    public String toString() {
        char[] posStr = {
                (char)((int)'a' + x),
                (char)((int)'1' + y)
        };
        return new String(posStr);
    }
}