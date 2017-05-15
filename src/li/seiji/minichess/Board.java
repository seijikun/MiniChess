package li.seiji.minichess;

import li.seiji.minichess.figure.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    /* BOARD DEFINITIONS */
    public static final int ROWS = 6;
    public static final int COLUMNS = 5;
    public static final String DEFAULT_BOARD = "kqbnr\n" +
            "ppppp\n" +
            ".....\n" +
            ".....\n" +
            "PPPPP\n" +
            "RNBQK";


    /**
     * Get a list of all possible moves for the given state.
     * @param state Current state for which to get all possible moves.
     * @param player Player to get all currently possible moves for.
     * @return List of all possible moves for the given player
     */
    public static List<Move> getPossibleMoves(State state, Player player) {
        List<Move> moves = new ArrayList<Move>();

        for(int x = 0; x < state.board.length; ++x) {
            for(int y = 0; y < state.board[x].length; ++y) {
                char identifier = Character.toLowerCase(state.board[x][y]);
                if(Player.parseIdentifier(state.board[x][y]) == player) {
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
        }

        return moves;
    }

}
