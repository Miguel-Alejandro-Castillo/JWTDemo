package ar.com.demo.jwt.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {

    @NotBlank(message = "Email es obligatorio")
    private String email;
    @NotBlank(message = "Password es obligatorio")
    private String password;
}
