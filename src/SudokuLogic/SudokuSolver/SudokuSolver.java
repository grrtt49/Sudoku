package SudokuLogic.SudokuSolver;

import SudokuLogic.SudokuBoard;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SudokuSolver {
    private SudokuBoard board;

    public SudokuBoard solve(SudokuBoard board) {
        solveRec(0, 0, board.clone());
        return this.board;
    }

    private boolean solveRec(int x, int y, SudokuBoard testBoard) {

        //go to next row if x is too large
        if(x > 8) {
            x = 0;
            y++;
        }

        //if y is too large, it reached the end so end the function and return
        if(y > 8) {
            board = testBoard;
            return true;
        }

        if(testBoard.getNumber(x, y) != 0) {
            return solveRec(x + 1, y, testBoard.clone());
        }

        Set<Integer> available = availableNumbers(x, y, testBoard);
        for(int n : available) {
            testBoard.insertNumber(x, y, n);
            if (solveRec(x + 1, y, testBoard.clone())) {
                return true;
            }
        }

        return false;
    }

    private Set<Integer> availableNumbers(int x, int y, SudokuBoard board) {
        Set<Integer> available = new HashSet<>(List.of(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
        available.removeAll(unavailableNumbers(x, y, board));
        return available;
    }

    private Set<Integer> unavailableNumbers(int x, int y, SudokuBoard board) {
        Set<Integer> unavailable = new HashSet<>();
        unavailable.addAll(unavailableInArray(board.getRow(y)));
        unavailable.addAll(unavailableInArray(board.getColumn(x)));
        unavailable.addAll(unavailableInArray(board.getSquareArr(x, y)));
        return unavailable;
    }

    private Set<Integer> unavailableInArray(int[] arr) {
        Set<Integer> unavailable = new HashSet<>();
        for (int j : arr) {
            if (j != 0) {
                unavailable.add(j);
            }
        }
        return unavailable;
    }


}
