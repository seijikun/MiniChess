package li.seiji.minichess.logging;

import li.seiji.minichess.Player;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.GameState;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.player.PlayerBase;

public class ConsoleLogger implements IGameLogger {

    private long nanoSecondsSum[] = new long[2];
    private PlayerBase white;
    private PlayerBase black;

    @Override
    public void start(Board board, PlayerBase white, PlayerBase black) {
        this.white = white;
        this.black = black;

        prettyPrintBoard(board, null);
        System.out.println();
    }

    @Override
    public void logMove(Board board, Move move, long nanoSeconds) {
        Player player = (board.state.turn == Player.WHITE) ? Player.BLACK : Player.WHITE;
        nanoSecondsSum[(player == Player.WHITE) ? 0 : 1] += nanoSeconds;
        System.out.println(player.toString() + ": " + move.toString() + " [@" + ((float)nanoSeconds / 1000.0f / 1000.0f) + "ms]");

        prettyPrintBoard(board, move);
        System.out.println();
    }

    @Override
    public void end(Board board) {
        int turns = board.state.turnCounter;

        System.out.println("After " + turns + " turns:");
        if(board.state.gameState.isDefinitiveWin()) {
            Player winner = (board.state.gameState == GameState.WIN_BLACK) ? Player.BLACK : Player.WHITE;
            System.out.println("Player " + winner + " won this match.");
        } else {
            System.out.println("The game ended in a draw.");
        }
        System.out.println();

        /* Player Info */
        System.out.println("WHITE [" + white.getClass().getSimpleName() + "]:");
        System.out.println("\tAverage Turn-Time: " + ((float)nanoSecondsSum[0] / (float)turns / 1000.0f / 1000.0f) );

        System.out.println("BLACK [" + black.getClass().getSimpleName() + "]:");
        System.out.println("\tAverage Turn-Time BLACK: " + ((float)nanoSecondsSum[1] / (float)turns / 1000.0f / 1000.0f) );

        System.out.println();
    }

    @Override
    public void close() {

    }







    /* LOGGING */
    private void prettyPrintBoard(Board board, Move move) {
        for(int y = 0; y < Board.ROWS; ++y) {
            System.out.print("| " + (Board.ROWS - y) + " |");
            for(int x = 0; x < Board.COLUMNS; ++x) {
                char identifier = board.state.board[y][x];
                System.out.print(' ');

                activatePlayerColor(board.state.board, x, y, move);
                System.out.print(identifier);
                IGameLogger.resetColor();

                System.out.print(" |");
            }
            System.out.println();
        }

        System.out.println("-------------------------");
        System.out.println("|   | a | b | c | d | e |");
    }

    private void activatePlayerColor(char[][] board, int x, int y, Move move) {
        Player player = Player.parseIdentifier(board[y][x]);

        if(move != null) {
            if(move.to.x == x && move.to.y == y) {
                if(move.toOldVal != '.')
                    IGameLogger.activateColor(IGameLogger.BACK_RED);
                else
                    IGameLogger.activateColor(IGameLogger.BACK_GREEN);
            } else if(move.from.x == x && move.from.y == y) {
                IGameLogger.activateColor(IGameLogger.BACK_GREEN);
            }
        }

        switch(player) {
            case WHITE:
                IGameLogger.activateColor(IGameLogger.FONT_BLUE);
                break;
            case BLACK:
                IGameLogger.activateColor(IGameLogger.FONT_BLACK);
                break;
            case NONE:
                IGameLogger.activateColor(IGameLogger.FONT_BLACK);
                break;
        }

    }

}
