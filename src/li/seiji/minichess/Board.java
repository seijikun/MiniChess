package li.seiji.minichess;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by seiji on 5/15/17.
 */
public class Board {

    Square[] squares;

    public List<Move> getPossibleMovesForPlayer(Team team) {
        throw new NotImplementedException();
    }

    public abstract static class Square {

        public int row;
        public int col;

        public

        public Square(int row, int col) {
            this.row = row;
            this.col = col;
        }

    }
}
