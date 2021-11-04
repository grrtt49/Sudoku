package requests;

import sudokuLogic.SudokuBoard;

public class ValidatePuzzleRequest {
    private SudokuBoard board;

    public ValidatePuzzleRequest(SudokuBoard board) {
        this.board = board;
    }

    public SudokuBoard getBoard() {
        return board;
    }
}
