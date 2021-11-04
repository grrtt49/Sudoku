package services;

import sudokuLogic.SudokuBoard;
import sudokuLogic.sudokuCreator.SudokuCreator;
import results.CreatePuzzleResult;

public class CreatePuzzleService {
    public CreatePuzzleResult create() {
        SudokuCreator creator = new SudokuCreator();
        creator.create();
        SudokuBoard puzzle = creator.getPuzzle();

        return new CreatePuzzleResult(puzzle.toArray(), null, true);
    }
}
