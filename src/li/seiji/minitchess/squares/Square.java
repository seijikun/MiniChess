package li.seiji.minitchess.squares;

import li.seiji.minitchess.Move;
import li.seiji.minitchess.Team;

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
