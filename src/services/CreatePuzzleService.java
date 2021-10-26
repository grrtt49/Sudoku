package services;

import SudokuLogic.SudokuBoard;
import SudokuLogic.SudokuCreator.SudokuCreator;
import results.CreatePuzzleResult;

public class CreatePuzzleService {
    public CreatePuzzleResult create() {
        SudokuCreator creator = new SudokuCreator();
        creator.create();
        SudokuBoard puzzle = creator.getPuzzle();
        SudokuBoard solution = creator.getSolution();

        return new CreatePuzzleResult(puzzle.toArray(), solution.toArray(), null, true);
    }
}
