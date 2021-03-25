package devinc.dwitter.exception;

public class OperationForbiddenException extends RuntimeException{
    public OperationForbiddenException(String message) {
        super(message);
    }
}
