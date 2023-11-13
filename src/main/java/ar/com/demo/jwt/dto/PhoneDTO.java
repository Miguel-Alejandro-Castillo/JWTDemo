package ar.com.demo.jwt.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PhoneDTO {
    private Long id;
    @NotNull(message = "Number Phone cannot be empty")
    private Long number;
    @NotNull(message = "City Code Phone cannot be empty")
    private Integer cityCode;
    @NotBlank(message = "Country Code Phone cannot be empty")
    private String countryCode;
}
