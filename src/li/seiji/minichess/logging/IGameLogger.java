package li.seiji.minichess.logging;

import com.sun.org.apache.regexp.internal.RE;
import li.seiji.minichess.board.Board;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.player.IPlayer;

import java.io.IOException;

public interface IGameLogger {

    String FONT_RESET = "\u001B[0m";
    String FONT_BLACK = "\u001B[30m";
    String FONT_RED = "\u001B[31m";
    String FONT_GREEN = "\u001B[32m";
    String FONT_BLUE = "\u001B[34m";
    String FONT_PURPLE = "\u001B[35m";
    String FONT_CYAN = "\u001B[36m";
    String FONT_WHITE = "\u001B[37m";

    String BACK_RESET = "";
    String BACK_BLACK = "\u001B[40m";
    String BACK_RED = "\u001B[41m";
    String BACK_GREEN = "\u001B[42m";
    String BACK_BLUE = "\u001B[44m";
    String BACK_PURPLE = "\u001B[45m";
    String BACK_CYAN = "\u001B[46m";
    String BACK_WHITE = "\u001B[47m";

    static void activateColor(String color) {
        System.out.print(color);
    }


    static void resetColor() {
        System.out.print(FONT_RESET);
        System.out.print(BACK_RESET);
    }

    static String withColor(String color, String line) {
        return color + line + FONT_RESET;
    }



    void start(Board board, IPlayer white, IPlayer black);

    void logMove(Board board, Move move, long nanoSeconds) throws IOException;

    void end(Board board);

    void close() throws IOException;

}
