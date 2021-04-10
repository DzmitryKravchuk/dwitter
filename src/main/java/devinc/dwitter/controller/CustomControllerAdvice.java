package devinc.dwitter.controller;

import devinc.dwitter.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler({NotExistingEMailException.class, NotUniqueUserLoginException.class,
            NotUniqueUserNameException.class, ObjectNotFoundException.class, OperationForbiddenException.class,
            PasswordIncorrectException.class})
    public ResponseEntity<String> handleException(RuntimeException e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
