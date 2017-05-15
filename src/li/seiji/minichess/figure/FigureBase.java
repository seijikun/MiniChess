package li.seiji.minichess.figure;

import li.seiji.minichess.Board;
import li.seiji.minichess.Move;
import li.seiji.minichess.Team;

import java.util.List;

public abstract class FigureBase {

    public Team team;
    public char identifier;

    public FigureBase(Team team, char identifier) {
        this.team = team;
        this.identifier = identifier;

        if(team == Team.BLACK) {
            this.identifier = Character.toLowerCase(identifier);
        } else {
            this.identifier = Character.toUpperCase(identifier);
        }
    }

    public abstract List<Move> getPossibleMoves(Board board);

    @Override
    public String toString() {
        return String.valueOf(identifier);
    }

}
