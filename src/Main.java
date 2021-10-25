import Sudoku.SudokuBoard;
import SudokuCreator.SudokuCreator;
import SudokuSolver.SudokuSolver;
import SudokuSolver.SudokuSolutionCounter;

public class Main {
    public static void main(String[] args) {
        //SudokuBoard board = new SudokuBoard("8..........36......7..9.2...5...7.......457.....1...3...1....68..85...1..9....4..");
        //SudokuBoard board = new SudokuBoard("123456789........................................................................");

        SudokuCreator creator = new SudokuCreator();
        SudokuBoard puzzle = creator.create();
        SudokuBoard solution = creator.getSolution();

        System.out.println(solution);
        System.out.println(puzzle);

        /*System.out.println(board);
        System.out.println(board.isValid());

        SudokuSolutionCounter counter = new SudokuSolutionCounter();
        System.out.println("Multiple solutions? " + counter.isMultipleSolutions(board));*/

        /*SudokuSolver solver = new SudokuSolver(board);
        SudokuBoard solved = solver.solve();
        System.out.println(solved);

        System.out.println(solver.getCount());
        System.out.println("Time (in ms): " + solver.getTime());*/

    }
}
