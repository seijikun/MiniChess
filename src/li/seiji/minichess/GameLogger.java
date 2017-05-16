package li.seiji.minichess;

import li.seiji.minichess.move.Move;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameLogger {
    private BufferedWriter writer;
    private BufferedReader reader;
    private File logFile = new File("game_log.txt");
    private Mode mode;


    public GameLogger(Mode mode) throws IOException {
        this.mode = mode;

        if(mode == Mode.WRITE)
            writer = new BufferedWriter(new FileWriter(logFile));
        else
            reader = new BufferedReader(new FileReader(logFile));

    }

    public void logMove(Move move) throws IOException {
        if(mode != Mode.WRITE)
            throw new RuntimeException("GameLogger has been created with Wrong Mode.");

        writer.write(move.toString() + "\n");
    }

    public List<Move> readLogFile() throws IOException {
        if(mode != Mode.READ)
            throw new RuntimeException("GameLogger has been created with Wrong Mode.");

        List<Move> moves = new ArrayList<>();
        for(String line; (line = reader.readLine()) != null;)
            moves.add(new Move(line));

        reader.close();

        return moves;
    }

    public void close() throws IOException {
        writer.close();
    }

    public enum Mode {
        READ, WRITE
    }
}
