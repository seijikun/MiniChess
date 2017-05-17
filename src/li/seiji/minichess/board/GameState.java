package li.seiji.minichess.board;

public enum GameState {
    ONGOING, WIN_WHITE, WIN_BLACK, TIE;

    public boolean isDefinitiveWin() {
        return (this == WIN_WHITE || this == WIN_BLACK);
    }
}
