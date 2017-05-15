package li.seiji.minichess.squares;

import li.seiji.minichess.Move;
import li.seiji.minichess.Team;

import java.util.List;

public abstract class Square {

    public Team team;
    public char identifier;

    public abstract List<Move> getPossibleMoves();

    public Square(Team team, char identifier) {
        this.team = team;
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return String.valueOf(identifier);
    }

}
