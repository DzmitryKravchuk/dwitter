package devinc.dwitter.exception;

public class NotUniqueUserLoginException extends RuntimeException{
    public NotUniqueUserLoginException(String s) {
        super(s);
    }
}
