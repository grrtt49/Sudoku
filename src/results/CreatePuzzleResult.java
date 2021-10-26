package results;

public class CreatePuzzleResult extends Result {
    private final int[][] puzzle;
    private final int[][] solution;

    public CreatePuzzleResult(int[][] puzzle, int[][] solution, String message, boolean success) {
        super(message, success);
        this.puzzle = puzzle;
        this.solution = solution;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }

    public int[][] getSolution() {
        return solution;
    }
}
