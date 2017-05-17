package li.seiji.minichess.logging;

import li.seiji.minichess.board.Board;
import li.seiji.minichess.move.Move;

public interface IGameLogger {

    void start(Board board);

    void logMove(Board board, Move move);

    void end(Board board);

    void close();

}
