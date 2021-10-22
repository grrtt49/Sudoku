public class Main {
    public static void main(String[] args) {
        SudokuBoard board = new SudokuBoard(3, 3, 3, 3);
        board.insertNumber(8, 4, 9);
        System.out.println(board);
    }
}
