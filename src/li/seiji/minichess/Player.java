package li.seiji.minichess;

public enum Player {

    WHITE,
    BLACK;

    public static Player parseIdentifier(char identifier) {
        return Character.isUpperCase(identifier) ? WHITE : BLACK;
    }

}
