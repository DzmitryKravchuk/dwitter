package devinc.dwitter.exception;

public class NotExistingEMailException extends RuntimeException{
    public NotExistingEMailException(String s) {
        super(s);
    }
}
