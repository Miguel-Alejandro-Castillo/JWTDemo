package ar.com.demo.jwt.exceptions;

public class UserNotExistsException extends ErrorCustomException {
    public UserNotExistsException(String errorMessage) {
        super(errorMessage, 6);
    }
}
