package ar.com.demo.jwt.controllers;

import ar.com.demo.jwt.dto.*;
import ar.com.demo.jwt.exceptions.ErrorCustomException;
import ar.com.demo.jwt.exceptions.UserExistsException;
import ar.com.demo.jwt.exceptions.UserInactiveException;
import ar.com.demo.jwt.exceptions.UserNotExistsException;
import ar.com.demo.jwt.model.User;
import ar.com.demo.jwt.services.JWTService;
import ar.com.demo.jwt.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO body) throws Exception {
        DetailErrorDTO detailErrorDTO = null;
        User user = this.usersService.findByEmailAndPassword(body.getEmail(), body.getPassword());
        if (user != null) {
            if (user.getIsActive()) {
                UserDTO userDTO = this.usersService.mapTo(user);
                //userDTO = this.jwtService.getToken(userDTO);
                String token = this.jwtService.createToken(userDTO);
                userDTO.setToken(token);
                userDTO.setLastLogin(LocalDateTime.now());
                userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                return ResponseEntity.ok(userDTO);
            } else {
                throw new UserInactiveException("Usuario con email y/o password inactivo");
            }
        } else {
            throw new UserInactiveException("Usuario con email y/o password inexistente");
        }
    }

    @PostMapping("/sign-up")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDTO body) throws Exception {
        User user = this.usersService.mapTo(body);
        if (this.usersService.findByEmail(user.getEmail()) == null) {
            user = this.usersService.save(user);
            UserDTO userDTO = this.usersService.mapTo(user);
            //response = this.jwtService.getToken(response);
            String token = this.jwtService.createToken(userDTO);
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(token);
            //response.setPassword(passwordEncoder.encode(response.getPassword()));
            return ResponseEntity.ok(tokenDTO);
        } else {
            throw new UserExistsException("Usuario con email existente");
        }

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserExistsException.class, UserInactiveException.class, UserNotExistsException.class})
    public ErrorDTO handleOtherValidationExceptions(ErrorCustomException ex) {
        DetailErrorDTO detailErrorDTO = new DetailErrorDTO();
        detailErrorDTO.setDetail(ex.getMessage());
        detailErrorDTO.setCodigo(ex.getCode());
        detailErrorDTO.setTimestamp(new Timestamp(System.currentTimeMillis()));
        List<DetailErrorDTO> details = new ArrayList<>();
        details.add(detailErrorDTO);
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError(details);
        return errorDTO;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
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
        return errorDTO;
    }

}