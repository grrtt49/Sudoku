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
        x = x % squareWidth;
        y = y % squareHeight;

        board[squareY][squareX].insertNumber(x, y, number);
    }

    @Override
    public String toString() {
        String output = "";
        for(int sy = 0; sy < squareHeight; sy++) {
            for(int sx = 0; sx < squareWidth; sx++) {
                for(int y = 0; y < height; y++) {
                    for(int x = 0; x < width; x++) {
                        output += board[sy][sx].getSquare()[y][x] + " ";
                    }
                }
                output += "\n";
            }
            output += "\n";
        }
        return output;
    }


}
