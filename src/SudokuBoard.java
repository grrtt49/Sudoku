import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuBoard {
    private SudokuSquare[][] board;
    private int height;
    private int width;
    private int squareHeight;
    private int squareWidth;

    public SudokuBoard(int height, int width, int squareHeight, int squareWidth) {
        this.height = height;
        this.width = width;
        this.squareHeight = squareHeight;
        this.squareWidth = squareWidth;

        board = new SudokuSquare[height][width];
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                board[y][x] = new SudokuSquare(squareHeight, squareWidth);
            }
        }
    }

    public void insertNumber(int x, int y, int number) {
        int squareX = x / squareWidth;
        int squareY = y / squareHeight;
        x = x % width;
        y = y % height;

        board[squareY][squareX].insertNumber(x, y, number);
    }

    @Override
    public String toString() {
        String blankLine = "-".repeat(((width * 2 + 1) * squareWidth) + 2);
        StringBuilder output = new StringBuilder(" " + blankLine + "\n");
        for(int sy = 0; sy < squareHeight; sy++) {
            for (int y = 0; y < height; y++) {
                ArrayList<Integer> line = new ArrayList<>();
                for (int sx = 0; sx < squareWidth; sx++) {
                    int[] row = board[sy][sx].getRow(y);
                    for (int x : row) {
                        line.add(x);
                    }
                }

                int count = 0;
                for(Integer n : line) {
                    if(count % width == 0) {
                        output.append("| ");
                    }
                    if(n == 0) {
                        output.append(". ");
                    }
                    else {
                        output.append(n).append(" ");
                    }
                    count++;
                }
                output.append("| \n");
            }
            output.append(" ").append(blankLine).append("\n");
        }
        return output.toString();
    }


}
