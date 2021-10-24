package SudokuCreator;

import Sudoku.SudokuBoard;

public class SudokuCreator {
    private SudokuBoard board;

    public SudokuCreator() {
        board = new SudokuBoard();
    }

    public SudokuBoard create() {
        return board;
    }
}
