package game;
import java.util.*;
import java.awt.*;

public class AnimatedDriver {
    public static void main(String[] args) {
        Collage board = new Collage();
        Font font = new Font("Arial", Font.PLAIN, 30);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(font);

        playFullGame(board);

        System.exit(0);
    }

    private static void playFullGame(Collage boardCollage) {
        Board board = new Board();
        board.updateOpenSpaces();
        board.addRandomTile();
        board.updateOpenSpaces();
        board.addRandomTile();

        boardCollage.updateBoard(board, 2);
        HashMap<Character, String> keyMap = new HashMap<>() {
            {
                put('w', "U");
                put('a', "L");
                put('s', "D");
                put('d', "R");
                put('W', "U");
                put('A', "L");
                put('S', "D");
                put('D', "R");
                put('q', "Exit");
            }
        };

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String action = keyMap.getOrDefault(StdDraw.nextKeyTyped(), "Invalid");
                if (action.equals("Exit"))
                    break;
                if (!action.equals("Invalid")) {
                    int[][] oldBoard = new int[4][4];
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            oldBoard[i][j] = board.getBoard()[i][j];
                        }
                    }
                    board.makeMove(action.charAt(0));
                    board.updateOpenSpaces();
                    if (!board.isGameLost() && !boardsMatch(oldBoard, board.getBoard())) {
                        board.addRandomTile();
                    }
                    boardCollage.updateBoard(board, 2);
                }
            }

            StdDraw.show();
            StdDraw.pause(20);
        }
    }

    private static boolean boardsMatch(int[][] board1, int[][] board2) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board1[i][j] != board2[i][j])
                    return false;
            }
        }
        return true;
    }
}
