package ar.com.demo.jwt.services;

import ar.com.demo.jwt.dto.UserDTO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.security.core.AuthenticationException;

@Service
public class JWTService {

    private static final String TOKEN_BEARER = "Bearer ";

    private final String TOKEN_HEADER = "Authorization";

    private  JwtParser jwtParser;

    @Value("${secretKey.token}")
    private String secretKey;

    @Value("${duracion.token.web}")
    private Long tokenWebDuracion;

    @PostConstruct
    public void init() {
        this.jwtParser = Jwts.parser().setSigningKey(this.secretKey);
    }

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
        userDTO.setToken(TOKEN_BEARER + jwtToken);
        return userDTO;
    }

    public String createToken(UserDTO userDTO) {
        Claims claims = Jwts.claims().setSubject(userDTO.getEmail());
        //claims.put("firstName", user.getFirstName());
        //claims.put("lastName", user.getLastName());
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(this.tokenWebDuracion));
        return  TOKEN_BEARER +
            Jwts.builder()
            .setClaims(claims)
            .setExpiration(tokenValidity)
            .signWith(SignatureAlgorithm.HS256, this.secretKey)
            .compact();
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_BEARER)) {
            return bearerToken.substring(TOKEN_BEARER.length());
        }
        return null;
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return this.parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }
}
