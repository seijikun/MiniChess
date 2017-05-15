package li.seiji.helpers;

import java.util.List;
import java.util.Random;

public class DataHelper {

    private static Random rand = new Random();

    public static <T> T selectRandom(List<T> src) {
        return src.get(rand.nextInt(src.size()));
    }

}
