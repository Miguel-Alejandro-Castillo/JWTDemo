package ar.com.demo.jwt.exceptions;

public class UserExistsException extends ErrorCustomException{

    public UserExistsException(String errorMessage) {
        super(errorMessage, 4);
    }

}
