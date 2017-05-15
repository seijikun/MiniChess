package li.seiji.minichess;

public enum Player {

    NONE,
    WHITE,
    BLACK;

    public static Player parseIdentifier(char identifier) {
        if(identifier == '.') return NONE;
        return Character.isUpperCase(identifier) ? WHITE : BLACK;
    }

}
