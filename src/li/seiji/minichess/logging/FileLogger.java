package li.seiji.minichess.logging;

import li.seiji.minichess.board.Board;
import li.seiji.minichess.board.State;
import li.seiji.minichess.move.Move;
import li.seiji.minichess.player.IPlayer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements IGameLogger {
    private BufferedWriter writer;

    public FileLogger(String path) throws IOException {
        File logFile = new File(path + "/game_log.txt");
        writer = new BufferedWriter(new FileWriter(logFile));
    }

    @Override
    public void start(Board board, IPlayer white, IPlayer black) {

    }

    @Override
    public void logMove(Board board, Move move, long nanoSeconds) throws IOException {
        writer.write(move.toString() + System.lineSeparator());
    }

    @Override
    public void end(Board board) {

    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
