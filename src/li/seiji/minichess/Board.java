package li.seiji.minichess;


import li.seiji.minichess.figure.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public static List<Move> getPossibleMoves(State state) {
        List<Move> moves = new ArrayList<Move>();

        for(int x = 0; x < state.board.length; ++x) {
            for(int y = 0; y < state.board[x].length; ++y) {
                char identifier = Character.toLowerCase(state.board[x][y]);
                Player player = Player.parseIdentifier(state.board[x][y]);

                switch(identifier) {
                    case Pawn.identifier:
                        Pawn.getPossibleMoves(state, new Square(x, y), moves);
                        break;
                    case Rook.identifier:
                        Rook.getPossibleMoves(state, new Square(x, y), moves);
                        break;
                    case Bishop.identifier:
                        Bishop.getPossibleMoves(state, new Square(x, y), moves);
                        break;
                    case Knight.identifier:
                        Knight.getPossibleMoves(state, new Square(x, y), moves);
                        break;
                    case King.identifier:
                        King.getPossibleMoves(state, new Square(x, y), moves);
                        break;
                    case Queen.identifier:
                        Queen.getPossibleMoves(state, new Square(x, y), moves);
                        break;
                }
            }
        }

        return moves;
    }

}
