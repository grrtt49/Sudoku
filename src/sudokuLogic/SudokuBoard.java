package sudokuLogic;

import java.util.HashSet;
import java.util.Set;

public class SudokuBoard {
    private SudokuSquare[][] board;
    private final int height;
    private final int width;
    private final int squareHeight;
    private final int squareWidth;

    public SudokuBoard() {
        this(3, 3, 3, 3);
    }

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

    public SudokuBoard(String boardStr) {
        this();
        int x = 0;
        int y = 0;
        for(int i = 0; i < boardStr.length(); i++) {
            char c = boardStr.charAt(i);
            int n = 0;
            if(c != '.')
                n = Integer.parseInt(boardStr.charAt(i) + "");

            insertNumber(x, y, n);
            x++;
            if(x > 8) {
                x = 0;
                y++;
            }
        }
    }

    public int getNumber(int x, int y) {
        int squareX = x / squareWidth;
        int squareY = y / squareHeight;
        x = x % width;
        y = y % height;

        return board[squareY][squareX].getNumber(x, y);
    }

    public void insertNumber(int x, int y, int number) {
        int squareX = x / squareWidth;
        int squareY = y / squareHeight;
        x = x % width;
        y = y % height;

        board[squareY][squareX].insertNumber(x, y, number);
    }

    public boolean isValid() {
        return allRowsValid() && allColumnsValid() && allSquaresValid();
    }

    private boolean allSquaresValid() {
        for(int y = 0; y < squareHeight; y++) {
            for(int x = 0; x < squareWidth; x++) {
                if(!isSquareValid(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSquareValid(int x, int y) {
        return board[y][x].isValid();
    }

    private boolean allColumnsValid() {
        for(int x = 0; x < squareWidth * width; x++) {
            if(!isColumnValid(x)) {
                return false;
            }
        }
        return true;
    }

    public boolean isColumnValid(int x) {
        return isArrayValid(getColumn(x));
    }

    private boolean allRowsValid() {
        for(int y = 0; y < squareHeight * height; y++) {
            if(!isRowValid(y)) {
                return false;
            }
        }
        return true;
    }

    public boolean isRowValid(int y) {
        return isArrayValid(getRow(y));
    }

    private boolean isArrayValid(int[] arr) {
        Set<Integer> found = new HashSet<>();
        for(int n : arr) {
            if(n != 0 && !found.add(n)) {
                return false;
            }
        }
        return true;
    }

    public SudokuSquare[][] getBoard() {
        return board;
    }

    public int[] getRow(int r) {
        int squareRow = r / squareHeight;
        r %= height;
        int[] row = new int[squareWidth * width];
        int index = 0;
        for(SudokuSquare s : board[squareRow]) {
            for(int n : s.getRow(r)) {
                row[index] = n;
                index++;
            }
        }
        return row;
    }

    public int[] getColumn(int c) {
        int squareColumn = c / squareWidth;
        c %= width;

        int[] column = new int[squareHeight * height];
        int index = 0;
        for(int i = 0; i < squareHeight; i++) {
            SudokuSquare s = board[i][squareColumn];
            for(int n : s.getColumn(c)) {
                column[index] = n;
                index++;
            }
        }
        return column;
    }

    public int[] getSquareArr(int x, int y) {
        x /= squareWidth;
        y /= squareHeight;
        return board[y][x].getSquareArr();
    }

    public boolean hasEmptySquare() {
        for(int sy = 0; sy < squareHeight; sy++) {
            for(int sx = 0; sx < squareWidth; sx++) {
                for(int y = 0; y < height; y++) {
                    for(int x = 0; x < width; x++) {
                        int n = getNumber(sx * squareWidth + x, sy * squareHeight + y);
                        if(n == 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public int[][] toArray() {
        int[][] array = new int[squareHeight * height][];
        for(int y = 0; y < squareHeight * height; y++) {
            array[y] = getRow(y);
        }
        return array;
    }

    @Override
    public String toString() {
        String blankLine = "-".repeat(((width * 2 + 1) * squareWidth) + 2);
        StringBuilder output = new StringBuilder(" " + blankLine + "\n");
        for(int sy = 0; sy < squareHeight; sy++) {
            for (int y = 0; y < height; y++) {
                int[] line = getRow((sy * height) + y);

                int count = 0;
                for(int n : line) {
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

    @Override
    public SudokuBoard clone() {
        SudokuBoard newBoard = new SudokuBoard(height, width, squareHeight, squareWidth);
        for(int sy = 0; sy < squareHeight; sy++) {
            for(int sx = 0; sx < squareWidth; sx++) {
                for(int y = 0; y < height; y++) {
                    for(int x = 0; x < width; x++) {
                        int n = getNumber(sx * squareWidth + x, sy * squareHeight + y);
                        if(n > 0) {
                            newBoard.insertNumber(sx * squareWidth + x, sy * squareHeight + y, n);
                        }
                    }
                }
            }
        }
        return newBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuBoard that = (SudokuBoard) o;
        if (height != that.height || width != that.width || squareHeight != that.squareHeight || squareWidth != that.squareWidth) return false;

        for(int sy = 0; sy < squareHeight; sy++) {
            for(int sx = 0; sx < squareWidth; sx++) {
                for(int y = 0; y < height; y++) {
                    for(int x = 0; x < width; x++) {
                        int n = getNumber(sx * squareWidth + x, sy * squareHeight + y);
                        int thatN = that.getNumber(sx * squareWidth + x, sy * squareHeight + y);
                        if(n != thatN) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
