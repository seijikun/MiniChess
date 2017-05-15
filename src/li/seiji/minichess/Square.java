package li.seiji.minichess;

import li.seiji.minichess.figure.FigureBase;

public class Square {

    public int row;
    public int col;

    public FigureBase figure = null;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

}