package ar.com.demo.jwt.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class DetailErrorDTO {
    private Timestamp timestamp;
    private Integer codigo;
    private  String detail;
}