package devinc.dwitter.exception;

public class NotUniqueUserNameException extends RuntimeException{
    public NotUniqueUserNameException(String s) {
        super(s);
    }
}
