package exceptions;

public class LockAccountException extends RuntimeException {
    public LockAccountException(String message) {
        super(message);
    }
}
