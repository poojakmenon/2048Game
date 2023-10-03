package game;

import java.util.ArrayList;

/**
 * 2048 Board
 **/
public class Board {
    private int[][] gameBoard;               // game board
    private ArrayList<BoardSpot> openSpaces; 

    /**
     * Zero-argument Constructor
     **/
    public Board() {
        gameBoard = new int[4][4];
        openSpaces = new ArrayList<>();
    }

    /**
     * One-argument Constructor
     * 
     * @param board board array with values to be passed through
     **/
    public Board ( int[][] board ) {
        gameBoard = new int[board.length][board[0].length];
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                gameBoard[r][c] = board[r][c]; 
            }
        }
        openSpaces = new ArrayList<>();
    }


    public void updateOpenSpaces() {
        openSpaces = new ArrayList<BoardSpot>();
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) { 
                if ( gameBoard[r][c] == 0 ) {
                    BoardSpot emptySpot = new BoardSpot(r,c);
                    openSpaces.add(emptySpot);
                }
            }
        }
    }

    /**
     * Adds random tile to an open spot with a 90% chance of a 2 value and a 10% chance of a 4 value
     **/
    public void addRandomTile() {
        int random = StdRandom.uniform(0,openSpaces.size());
        BoardSpot randomOpenTile = openSpaces.get(random);
        double randomValue = StdRandom.uniform(0.0,1.0);
        if (randomValue <= 0.9) {
            gameBoard[randomOpenTile.getRow()][randomOpenTile.getCol()] = 2;
        } else {
            gameBoard[randomOpenTile.getRow()][randomOpenTile.getCol()] = 4;
        }
    }

    /**
     * Swipes the board left
     **/
    public void swipeLeft() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 1; c < gameBoard[r].length; c++ ) {
                if (gameBoard[r][c-1] == 0 && gameBoard[r][c] != 0) {
                    for ( int i = c; i > 0; i--) {
                        if (gameBoard[r][i-1] == 0 && gameBoard[r][i] != 0) {
                            gameBoard[r][i-1] = gameBoard[r][i];
                            gameBoard[r][i] = 0;
                        }
                    }
                }   
            }
        }
    }

    /*
     * when swiping left, method will merge same numbers that are adjacent and form new tile of double value
     **/
    public void mergeLeft() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 1; c < gameBoard[r].length; c++ ) {
                if ( gameBoard[r][c] == gameBoard[r][c-1] ) {
                    gameBoard[r][c-1] = 2*gameBoard[r][c];
                    gameBoard[r][c] = 0;
                }
            }
            updateOpenSpaces();
        }
    }

    /**
     * Rotates board clockwise
     **/
    public void rotateBoard() {
        transpose();
        flipRows();
    }

    /**
     * Updates gameBoard instance variable as its transpose
     **/
    public void transpose() {
        int[][] oldGameBoard = new int [4][4];
        for ( int r = 0; r < gameBoard.length; r++) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                oldGameBoard[r][c] = gameBoard[r][c];
            }
        }
        for ( int r = 0; r < gameBoard.length; r++) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                gameBoard[r][c] = oldGameBoard[c][r];
            }
        }
    }

    /**
     * Updates gameBoard instance variable as its reverse
     **/
    public void flipRows() {
        int[][] oldGameBoard = new int [4][4];
        for ( int r = 0; r < gameBoard.length; r++) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                oldGameBoard[r][c] = gameBoard[r][c];
            }
        }
        for ( int r = 0; r < gameBoard.length; r++) {
            int col = gameBoard[r].length - 1;
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                gameBoard[r][c] = oldGameBoard[r][col];
                col--;
            }
        }
    }

    /**
     * Swipe numbers, merge neighbors, swipe [resulting tile in the direction of swipe]
     * @param letter 'L' for left, 'U' for up, 'R' for right, or 'D' for down
     **/
    public void makeMove(char letter) {
        if (letter == 'L') {
            swipeLeft();
            mergeLeft();
            swipeLeft();
        } else if (letter == 'U') {
            rotateBoard();
            rotateBoard();
            rotateBoard();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            rotateBoard();
        } else if (letter == 'R') {
            rotateBoard();
            rotateBoard();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            rotateBoard();
            rotateBoard();
        } else if (letter == 'D') {
            rotateBoard();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            rotateBoard();
            rotateBoard();
            rotateBoard();
        } else {
            return;
        }
    }

    /**
     * Returns true when no more empty game board spaces
     * @return the status of the game -- lost or not lost
     **/
    public boolean isGameLost() {
        return openSpaces.size() == 0;
    }

    /**
     * Shows final score
     **/
    public int showScore() {
        int score = 0;
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                score += gameBoard[r][c];
            }
        }
        return score;
    }

    /**
     * assists in printing to terminal in text driver
     **/
    public void print() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                String g = Integer.toString(gameBoard[r][c]);
                StdOut.print((g.equals("0")) ? "-" : g);
                for ( int o = 0; o < (5 - g.length()); o++ ) {
                    StdOut.print(" ");
                }
            }
            StdOut.println();
        }
    }
    /**
     * assists in printing to terminal in text driver
     **/
    public void printOpenSpaces() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                String g = Integer.toString(gameBoard[r][c]);
                for ( BoardSpot bs : getOpenSpaces() ) {
                    if (r == bs.getRow() && c == bs.getCol()) {
                        g = "**";
                    }
                }
                StdOut.print((g.equals("0")) ? "-" : g);
                for ( int o = 0; o < (5 - g.length()); o++ ) {
                    StdOut.print(" ");
                }
            }
            StdOut.println();
        }
    }
    

    public ArrayList<BoardSpot> getOpenSpaces() {
        return openSpaces;
    }

    public int[][] getBoard() {
        return gameBoard;
    }
}
