package Test;

import SudokuLogic.SudokuBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidityTest {
    private SudokuBoard board;
    int[] row;
    int[] column;

    @BeforeEach
    public void setUp() {
        board = new SudokuBoard();
        row = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        column = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    }

    @Test
    public void testGetRows() {
        setRow(0, row);
        setRow(4, row);
        setRow(8, row);
        assertArrayEquals(board.getRow(0), row);
        assertArrayEquals(board.getRow(4), row);
        assertArrayEquals(board.getRow(8), row);
    }

    @Test
    public void testGetColumns() {
        setColumn(0, column);
        setColumn(4, column);
        setColumn(8, column);
        assertArrayEquals(board.getColumn(0), column);
        assertArrayEquals(board.getColumn(4), column);
        assertArrayEquals(board.getColumn(8), column);
    }

    @Test
    public void testSquareValidity() {
        board.insertNumber(0, 0, 9);
        assertTrue(board.isValid());
        board.insertNumber(0, 1, 8);
        assertTrue(board.isValid());
        board.insertNumber(1, 1, 9);
        assertFalse(board.isValid());
    }

    @Test
    public void testColumnValidity() {
        board.insertNumber(0, 0, 9);
        assertTrue(board.isValid());
        board.insertNumber(0, 1, 8);
        assertTrue(board.isValid());
        board.insertNumber(0, 8, 9);
        assertFalse(board.isValid());
    }

    @Test
    public void testRowValidity() {
        board.insertNumber(0, 0, 9);
        assertTrue(board.isValid());
        board.insertNumber(1, 0, 8);
        assertTrue(board.isValid());
        board.insertNumber(8, 0, 9);
        assertFalse(board.isValid());
    }

    @Test
    public void cloneTest() {
        SudokuBoard clone = board.clone();
        assertEquals(board, clone);
        clone.insertNumber(5,5,7);
        assertNotEquals(board, clone);
    }

    private void setRow(int row, int[] values) {
        for(int i = 0; i < values.length; i++) {
            board.insertNumber(i, row, values[i]);
        }
    }

    private void setColumn(int column, int[] values) {
        for(int i = 0; i < values.length; i++) {
            board.insertNumber(column, i, values[i]);
        }
    }
}
