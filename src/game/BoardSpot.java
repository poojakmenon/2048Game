package game;

/**
 open board spots data
 */

public class BoardSpot {
    private int row;
    private int col;

    /**
     * default constructor
     */
    public BoardSpot() {
        row = 0;
        col = 0;
    }

    /**
     * constructor with row and column values
     */
    public BoardSpot(int rowVal, int columnVal) {
        row = rowVal;
        col = columnVal;
    }

    /**
     * Obtains the row of a board spot.
     */
    public int getRow() {
        return row;
    }

    /**
     * Obtains the column of a board spot.
     */
    public int getCol() {
        return col;
    }

    /**
     * object comparison
     */
    public boolean equals (Object other) {

        if ( other instanceof BoardSpot ) {
            BoardSpot o = (BoardSpot) other;
            return row == o.row && col == o.col;
        } else {
            return false;
        }
    }
}
