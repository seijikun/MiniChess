package li.seiji.minichess.figure;

import li.seiji.minichess.Board;
import li.seiji.minichess.Move;
import li.seiji.minichess.Team;

import java.util.List;

public class Bishop extends FigureBase {

    public Bishop(Team team){
        super(team);
    }

    @Override
    public List<Move> getPossibleMoves(Board board) {
        return null;
    }

}
