package results;

public class ValidatePuzzleResult extends Result {
    private boolean solvedCorrect;

    public ValidatePuzzleResult(boolean solvedCorrect, String message, boolean success) {
        super(message, success);
        this.solvedCorrect = solvedCorrect;
    }

    public boolean isSolvedCorrect() {
        return solvedCorrect;
    }

    public void setSolvedCorrect(boolean solvedCorrect) {
        this.solvedCorrect = solvedCorrect;
    }
}
