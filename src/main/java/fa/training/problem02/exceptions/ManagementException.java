package fa.training.problem02.exceptions;

public class ManagementException extends RuntimeException {
    public ManagementException(String message) {
        super(message);
    }

    public ManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}
