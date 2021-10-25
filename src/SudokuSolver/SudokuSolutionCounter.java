package SudokuSolver;

import Sudoku.SudokuBoard;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SudokuSolutionCounter {
    private int solutions;
    private double time;

    public SudokuSolutionCounter() {}

    public boolean isMultipleSolutions(SudokuBoard board) {
        solutions = 0;
        long startTime = System.nanoTime();
        //SudokuBoard easyBoard = findEasy(board.clone());
        //System.out.println("Easy board: \n" + easyBoard);
        solveRec(0, 0, board.clone());
        long endTime = System.nanoTime();
        time = (endTime - startTime) / 1000000.0;
        System.out.println("Found " + solutions + " solutions in " + time + " milliseconds.");
        return solutions > 1;
    }

    private SudokuBoard findEasy(SudokuBoard board) {
        boolean added;
        do {
            added = false;
            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++) {
                    if(board.getNumber(x, y) == 0) {
                        Set<Integer> available = availableNumbers(x, y, board);
                        if (available.size() == 1) {
                            board.insertNumber(x, y, available.iterator().next());
                            added = true;
                        }
                    }
                }
            }
        } while(added);
        return board;
    }

    public double getTime() {
        return time;
    }

    public int numSolutions(SudokuBoard board) {
        solutions = 0;
        solveRec(0, 0, board.clone());
        return solutions;
    }

    private void solveRec(int x, int y, SudokuBoard board) {
        SudokuBoard testBoard = findEasy(board);

        //go to next row if x is too large
        if(x > 8) {
            x = 0;
            y++;
        }

        //if y is too large, it reached the end so end the function and return
        if(y > 8) {
            solutions++;
            return;
        }

        if(testBoard.getNumber(x, y) != 0) {
            solveRec(x + 1, y, testBoard.clone());
            return;
        }

        for(int n = 9; n >= 1; n--) {
            testBoard.insertNumber(x, y, n);
            if(testBoard.isValid()) {
                solveRec(x + 1, y, testBoard.clone());
                if(solutions > 1) {
                    return;
                }
            }
        }

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
