import sudokuLogic.SudokuBoard;
import sudokuLogic.sudokuCreator.SudokuCreator;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //SudokuBoard board = new SudokuBoard("8..........36......7..9.2...5...7.......457.....1...3...1....68..85...1..9....4..");

        SudokuCreator creator = new SudokuCreator();
        creator.create();
        SudokuBoard puzzle = creator.getPuzzle();
        SudokuBoard solution = creator.getSolution();

        System.out.println(solution);
        System.out.println(Arrays.deepToString(solution.toArray()));
    }
}
