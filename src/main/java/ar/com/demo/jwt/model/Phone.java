package ar.com.demo.jwt.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PHONES")
public class Phone {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NUMBER")
    private Long number;

    @Column(name = "CITY_CODE")
    private Integer cityCode;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

}
