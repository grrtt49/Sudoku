public class SudokuSquare {
    private int[][] square;

    public SudokuSquare(int height, int width) {
        square = new int[height][width];
    }

    public int[][] getSquare() {
        return square;
    }

    public int[] getRow(int index) {
        return square[index];
    }

    public int[] getColumn(int index) {
        int[] col = new int[square.length];
        for(int i = 0; i < square.length; i++) {
            col[i] = square[i][index];
        }
        return col;
    }

    public void insertNumber(int x, int y, int number) {
        square[x][y] = number;
    }
}
