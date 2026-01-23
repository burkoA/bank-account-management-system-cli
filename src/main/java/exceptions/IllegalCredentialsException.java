package exceptions;

public class IllegalCredentialsException extends RuntimeException {
    public IllegalCredentialsException(String message) {
        super(message);
    }
}
