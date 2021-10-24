import Sudoku.SudokuBoard;
import SudokuSolver.SudokuSolver;

public class Main {
    public static void main(String[] args) {
        SudokuBoard board = new SudokuBoard();
        board.insertNumber(0, 0, 8);
        board.insertNumber(2, 1, 3);
        board.insertNumber(3, 1, 6);
        board.insertNumber(1, 2, 7);
        board.insertNumber(4, 2, 9);
        board.insertNumber(6, 2, 2);
        board.insertNumber(1, 3, 5);
        board.insertNumber(5, 3, 7);
        board.insertNumber(4, 4, 4);
        board.insertNumber(5, 4, 5);
        board.insertNumber(6, 4, 7);
        board.insertNumber(3, 5, 1);
        board.insertNumber(7, 5, 3);
        board.insertNumber(2, 6, 1);
        board.insertNumber(7, 6, 6);
        board.insertNumber(8, 6, 8);
        board.insertNumber(2, 7, 8);
        board.insertNumber(3, 7, 5);
        board.insertNumber(7, 7, 1);
        board.insertNumber(1, 8, 9);
        board.insertNumber(6, 8, 4);



        /*board.insertNumber(2, 0, 4);
        board.insertNumber(4, 0, 5);
        board.insertNumber(0, 1, 9);
        board.insertNumber(3, 1, 7);
        board.insertNumber(4, 1, 3);
        board.insertNumber(5, 1, 4);
        board.insertNumber(6, 1, 6);
        board.insertNumber(2, 2, 3);
        board.insertNumber(4, 2, 2);
        board.insertNumber(5, 2, 1);
        board.insertNumber(7, 2, 4);
        board.insertNumber(8, 2, 9);
        board.insertNumber(1, 3, 3);
        board.insertNumber(2, 3, 5);
        board.insertNumber(4, 3, 9);
        board.insertNumber(6, 3, 4);
        board.insertNumber(7, 3, 8);
        board.insertNumber(1, 4, 9);
        board.insertNumber(7, 4, 3);
        board.insertNumber(1, 5, 7);
        board.insertNumber(2, 5, 6);
        board.insertNumber(4, 5, 1);
        board.insertNumber(6, 5, 9);
        board.insertNumber(7, 5, 2);
        board.insertNumber(0, 6, 3);
        board.insertNumber(1, 6, 1);
        board.insertNumber(3, 6, 9);
        board.insertNumber(4, 6, 7);
        board.insertNumber(6, 6, 2);
        board.insertNumber(2, 7, 9);
        board.insertNumber(3, 7, 1);
        board.insertNumber(4, 7, 8);
        board.insertNumber(5, 7, 2);
        board.insertNumber(8, 7, 3);
        board.insertNumber(4, 8, 6);
        board.insertNumber(6, 8, 1);*/

        System.out.println(board);
        System.out.println(board.isValid());

        SudokuSolver solver = new SudokuSolver(board);
        SudokuBoard solved = solver.solve();
        System.out.println(solved);

        System.out.println(solver.getCount());
        System.out.println("Time (in ms): " + solver.getTime());

        /*SudokuBoard cloneTest = board.clone();
        cloneTest.insertNumber(8, 8, 9);
        System.out.println(board);
        System.out.println(cloneTest);*/

    }
}
