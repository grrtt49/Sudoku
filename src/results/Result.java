package results;

public class Result {
    private String message;
    private boolean success;

    /**
     * Creates an object representing the JSON data of the results of a request
     * @param message message of the result
     * @param success true if it was successful
     */
    public Result(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "Result{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}

