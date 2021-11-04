package services;

import requests.ValidatePuzzleRequest;
import results.ValidatePuzzleResult;
import sudokuLogic.SudokuBoard;

public class ValidatePuzzleService {
    public ValidatePuzzleResult validate(ValidatePuzzleRequest request) {
        SudokuBoard board = request.getBoard();
        if(board.hasEmptySquare()) {
            return new ValidatePuzzleResult(false, null, true);
        }
        return  new ValidatePuzzleResult(true, null, true);
    }
}
