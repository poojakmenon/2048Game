package game;

import java.awt.Color;
import java.awt.Font;

/**
 * Uses libraries and content from the Introduction to Computer Science book.   
 * Displays board interactively through rendering images with StdDraw.
 * @author Kal Pandit
 **/
public class Collage {
    private int n;

    /**
     * default board collage constructor
     */
    public Collage() {
        n = 4;
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setScale(0, 4);
        StdDraw.enableDoubleBuffering();
    }

    /**
     * board collage constructor of size n x n
     * 
     * @param size the number of rows and columns in the board
     */
    public Collage(int size) {
        n = size;
        StdDraw.setScale(0, n);
        StdDraw.enableDoubleBuffering();

    }

    /**
     * Updates and refreshes the gb
     */
    public void updateBoard(Board gb, int mode) {
        int[][] board = gb.getBoard();
        StdDraw.clear();
        if (mode == 2 && gb.isGameLost()) {
            Font font = new Font("Arial", Font.BOLD, 64);
            StdDraw.setPenColor(Color.BLACK);
            String text = "Final score: " + gb.showScore();
            StdDraw.setFont(font);
            StdDraw.text((double) n / 2, (double) n / 2, text);
        } else {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    replaceTile(i, j, board[i][j]);
                }
            }
        }
        StdDraw.show();
    }

    /**
     * Replaces and draws tiles to be replaces
     */
    public void replaceTile(int collageCol, int collageRow, int numToReplace) {
        if (numToReplace == 0) {
            drawEmptyTile(collageRow, collageCol);
        } else {
            drawNumberTile(collageRow, collageCol, numToReplace);
        }
    }
    
    private void drawEmptyTile(int collageRow, int collageCol) {
        double centerX = collageRow + 0.5;
        double centerY = (n - 1 - collageCol) + 0.5;
    
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.001);
        StdDraw.square(centerX, centerY, 0.5);
    }
    
    private void drawNumberTile(int collageRow, int collageCol, int numToReplace) {
        String text = (numToReplace == -1) ? "-" : Integer.toString(numToReplace);
        Color tileColor = getTileColor(numToReplace);
    
        double centerX = collageRow + 0.5;
        double centerY = (n - 1 - collageCol) + 0.5;
    
        StdDraw.setPenRadius();
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.square(centerX, centerY, 0.5);
    
        StdDraw.setPenColor(tileColor);
        StdDraw.filledSquare(centerX, centerY, 0.5);
    
        if (numToReplace != -1) {
            StdDraw.setPenColor(Color.WHITE);
            Font font = new Font("Arial", Font.BOLD, 40);
            StdDraw.setFont(font);
            StdDraw.text(centerX, centerY, text);
        }
    }
    
    private Color getTileColor(int numToReplace) {
        switch (numToReplace) {
            case -1:
                return Color.DARK_GRAY;
            case 2:
                return new Color(238, 228, 218);
            case 4:
                return new Color(255, 224, 200);
            case 8:
                return new Color(242, 177, 121);
            case 16:
                return new Color(245, 149, 99);
            case 32:
                return new Color(255, 160, 96);
            case 64:
                return new Color(246, 124, 96);
            default:
                return new Color(237, 197, 63);
        }

    }
}
