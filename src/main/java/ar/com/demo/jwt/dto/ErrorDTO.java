package ar.com.demo.jwt.dto;

import lombok.Data;

import java.util.List;

@Data
public class ErrorDTO {
    private List<DetailErrorDTO> error;
}
