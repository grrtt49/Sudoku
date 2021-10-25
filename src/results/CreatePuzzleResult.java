package results;

public class CreatePuzzleResult extends Result {
    private final int[][] puzzle;
    private final int[][] solution;

    public CreatePuzzleResult(String message, boolean success, int[][] puzzle, int[][] solution) {
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
