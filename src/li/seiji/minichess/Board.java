package li.seiji.minichess;


import li.seiji.minichess.figure.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public static final int ROWS = 6;
    public static final int COLUMNS = 5;

    public static List<Move> getPossibleMoves(State state) {
        List<Move> moves = new ArrayList<Move>();

        for(int x = 0; x < state.board.length; ++x) {
            for(int y = 0; y < state.board[x].length; ++y) {
                char identifier = Character.toLowerCase(state.board[x][y]);
                Player player = Player.parseIdentifier(state.board[x][y]);

                switch(identifier) {
                    case Pawn.identifier:
                        Pawn.getPossibleMoves(state, new Square(x, y), player, moves);
                        break;
                    case Rook.identifier:
                        Rook.getPossibleMoves(state, new Square(x, y), player, moves);
                        break;
                    case Bishop.identifier:
                        Bishop.getPossibleMoves(state, new Square(x, y), player, moves);
                        break;
                    case Knight.identifier:
                        Knight.getPossibleMoves(state, new Square(x, y), player, moves);
                        break;
                    case King.identifier:
                        King.getPossibleMoves(state, new Square(x, y), player, moves);
                        break;
                    case Queen.identifier:
                        Queen.getPossibleMoves(state, new Square(x, y), player, moves);
                        break;
                }
            }
        }

        return moves;
    }

}
