package li.seiji.minichess;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by seiji on 5/15/17.
 */
public class Board {

    Square[][] squares = new Square[6][5];

    public List<Move> getPossibleMovesForPlayer(Team team) {
        throw new NotImplementedException();
    }

}
