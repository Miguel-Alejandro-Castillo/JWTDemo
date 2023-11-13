package ar.com.demo.jwt.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @Column(name = "CREATED", length = 50)
    private LocalDateTime created;

    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @Column(name = "LAST_LOGIN", length = 50)
    private LocalDateTime lastLogin;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "PHONE_ID")
    private List<Phone> phones;

}