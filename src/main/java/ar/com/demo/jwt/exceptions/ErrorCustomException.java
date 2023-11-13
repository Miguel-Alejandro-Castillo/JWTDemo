package ar.com.demo.jwt.exceptions;

import lombok.Data;

@Data
public class ErrorCustomException extends Exception {
    private Integer code;
    public ErrorCustomException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
