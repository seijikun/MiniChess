package li.seiji.helpers;

import com.sun.media.sound.InvalidFormatException;
import li.seiji.minichess.move.Move;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameHelper {

    public static List<Move> readLogFromFile(String path) throws IOException {
        File logFile = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(logFile));

        List<Move> moves = new ArrayList<>();
        for(String line; (line = reader.readLine()) != null;)
            moves.add(new Move(line));

        return moves;
    }

    public static List<Move> readLogFromString(String log) throws InvalidFormatException {
        Scanner scanner = new Scanner(log);

        List<Move> moves = new ArrayList<>();
        String line;
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            moves.add(new Move(line));
        }

        return moves;
    }
}
