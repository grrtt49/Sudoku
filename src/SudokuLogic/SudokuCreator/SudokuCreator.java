package SudokuLogic.SudokuCreator;

import SudokuLogic.SudokuBoard;
import SudokuLogic.SudokuSolver.SudokuSolutionCounter;

import java.util.*;

public class SudokuCreator {
    private SudokuBoard board;
    private SudokuBoard puzzle;

    public SudokuCreator() {
        board = new SudokuBoard();
    }

    public void create() {
        setUpRandom();
        generateRec(0, 0, board.clone());
        createPuzzle(board.clone());
    }

    public SudokuBoard getSolution() {
        return board;
    }

    public SudokuBoard getPuzzle() {
        return puzzle;
    }

    private void setUpRandom() {
        ArrayList<Integer> digits = new ArrayList<Integer>(List.of(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
        Collections.shuffle(digits);
        for(int n : digits) {
            int x;
            int y;
            do {
                x = (int)(Math.random() * 9);
                y = (int)(Math.random() * 9);
            } while(board.getNumber(x, y) != 0);

            board.insertNumber(x, y, n);
        }
    }

    private boolean generateRec(int x, int y, SudokuBoard testBoard) {

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
            return generateRec(x + 1, y, testBoard.clone());
        }

        ArrayList<Integer> digits = new ArrayList<Integer>(List.of(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
        Collections.shuffle(digits);
        for(int n : digits) {
            testBoard.insertNumber(x, y, n);
            if (testBoard.isValid() && generateRec(x + 1, y, testBoard.clone())) {
                return true;
            }
        }

        return false;
    }

    private void createPuzzle(SudokuBoard testBoard) {
        SudokuSolutionCounter counter = new SudokuSolutionCounter();
        int numAdded = 1;

        while(numAdded > 0) {//28
            numAdded = 0;
            ArrayList<Integer[]> positions = getRandPositions();
            for (Integer[] pos : positions) {
                int x = pos[0];
                int y = pos[1];
                int previousNumber = testBoard.getNumber(x, y);
                if(previousNumber != 0) {
                    testBoard.insertNumber(x, y, 0);
                    numAdded++;

                    Set<Integer> available = availableNumbers(x, y, testBoard);
                    if (available.size() != 1) {
                        testBoard.insertNumber(x, y, previousNumber);
                        numAdded--;
                    }
                }
            }
        }

        ArrayList<Integer[]> positions = getRandPositions();
        for (Integer[] pos : positions) {
            int x = pos[0];
            int y = pos[1];
            int previousNumber = testBoard.getNumber(x, y);
            if(previousNumber != 0) {
                testBoard.insertNumber(x, y, 0);
                if(counter.isMultipleSolutions(testBoard)) {
                    testBoard.insertNumber(x, y, previousNumber);
                }
            }
        }

        puzzle = testBoard.clone();
    }

    private ArrayList<Integer[]> getRandPositions() {
        ArrayList<Integer[]> positions = new ArrayList<>();

        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                positions.add(new Integer[]{x, y});
            }
        }

        Collections.shuffle(positions);
        return positions;
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
