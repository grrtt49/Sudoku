package results;

public class CreatePuzzleResult extends Result {
    private final int[][] puzzle;

    public CreatePuzzleResult(int[][] puzzle, String message, boolean success) {
        super(message, success);
        this.puzzle = puzzle;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }
}
