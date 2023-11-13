package ar.com.demo.jwt.exceptions;

public class UserInactiveException extends ErrorCustomException {

    public UserInactiveException(String errorMessage) {
        super(errorMessage, 5);
    }
}
