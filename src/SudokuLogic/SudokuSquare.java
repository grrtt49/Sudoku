package SudokuLogic;

import java.util.HashSet;
import java.util.Set;

public class SudokuSquare {
    private int[][] square;

    public SudokuSquare(int height, int width) {
        square = new int[height][width];
    }

    public int[] getSquareArr() {
        int[] arr = new int[square.length * square[0].length];
        int index = 0;
        for (int[] ints : square) {
            for (int anInt : ints) {
                arr[index] = anInt;
                index++;
            }
        }
        return arr;
    }

    public int getNumber(int x, int y) {
        return square[y][x];
    }

    public int[] getRow(int index) {
        return square[index];
    }

    public int[] getColumn(int index) {
        int[] col = new int[square.length];
        for(int i = 0; i < square.length; i++) {
            col[i] = square[i][index];
        }
        return col;
    }

    public void insertNumber(int x, int y, int number) {
        square[y][x] = number;
    }

    public boolean isValid() {
        Set<Integer> found = new HashSet<>();
        for(int[] row : square) {
            for(int n : row) {
                if (n != 0 && !found.add(n)) {
                    return false;
                }
            }
        }
        return true;
    }
}
