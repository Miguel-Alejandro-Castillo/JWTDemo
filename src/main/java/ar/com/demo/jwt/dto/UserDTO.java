package ar.com.demo.jwt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String name;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private LocalDateTime created;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private LocalDateTime lastLogin;
    private Boolean isActive;
    private String token;
    private List<@Valid PhoneDTO> phones;
}
