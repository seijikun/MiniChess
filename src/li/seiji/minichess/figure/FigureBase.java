package li.seiji.minichess.figure;

import li.seiji.minichess.Board;
import li.seiji.minichess.Move;
import li.seiji.minichess.Team;

import java.util.List;

public abstract class FigureBase {

    public Team team;

    public FigureBase(Team team) {
        this.team = team;
    }

    public abstract List<Move> getPossibleMoves(Board board);

}
