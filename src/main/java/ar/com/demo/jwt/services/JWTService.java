package ar.com.demo.jwt.services;

import ar.com.demo.jwt.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JWTService {

    private static final String BEARER = "Bearer ";

    @Value("${secretKey.token}")
    private String secretKey;

    @Value("${duracion.token.web}")
    private Long tokenWebDuracion;

    public UserDTO getToken(UserDTO userDTO) {

        List<UserDTO> usuarios = new ArrayList<UserDTO>();

        //long tokenDurationMillis = 15 * 60 * 1000; // 15 minutos en milisegundos
        long expirationTimeMillis = System.currentTimeMillis() + tokenWebDuracion;

        String jwtToken = Jwts
            .builder()
            .setId("DemoJWT")
            .setSubject(userDTO.getEmail())
            .claim("authorities", usuarios.stream().map(authority -> authority.getName()).collect(Collectors.toList()))
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(expirationTimeMillis))
            .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
            .compact();
        userDTO.setToken(BEARER + jwtToken);
        return userDTO;
    }
}
