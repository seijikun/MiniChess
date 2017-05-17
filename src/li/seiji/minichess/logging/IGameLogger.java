package li.seiji.minichess.logging;

import li.seiji.minichess.board.Board;
import li.seiji.minichess.move.Move;

import java.io.IOException;

public interface IGameLogger {

    void start(Board board);

    void logMove(Board board, Move move) throws IOException;

    void end(Board board);

    void close() throws IOException;

}
