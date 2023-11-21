package ar.com.demo.jwt.exceptions;

import ar.com.demo.jwt.dto.DetailErrorDTO;
import ar.com.demo.jwt.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<DetailErrorDTO> details = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            DetailErrorDTO detailErrorDTO = new DetailErrorDTO();
            detailErrorDTO.setDetail(err.getDefaultMessage());
            detailErrorDTO.setCodigo(1);
            detailErrorDTO.setTimestamp(new Timestamp(System.currentTimeMillis()));
            details.add(detailErrorDTO);
        });
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError(details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserExistsException.class, UserInactiveException.class, UserNotExistsException.class})
    public ResponseEntity<ErrorDTO> handleOtherValidationExceptions(ErrorCustomException ex) {
        DetailErrorDTO detailErrorDTO = new DetailErrorDTO();
        detailErrorDTO.setDetail(ex.getMessage());
        detailErrorDTO.setCodigo(ex.getCode());
        detailErrorDTO.setTimestamp(new Timestamp(System.currentTimeMillis()));
        List<DetailErrorDTO> details = new ArrayList<>();
        details.add(detailErrorDTO);
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError(details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }
}
