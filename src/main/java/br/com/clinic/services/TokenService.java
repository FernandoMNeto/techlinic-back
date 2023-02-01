package br.com.clinic.services;

import java.util.Date;

import br.com.clinic.entities.models.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

@Service
public class TokenService {

    @Value("${clinic.jwt.expiration}")
    private String expiration;
    @Value("${clinic.jwt.secret}")
    private String secret;

    public TokenService() {}

    public String generateToken(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("techlinic")
                .setSubject(user.getUsername())
                .claim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret.getBytes()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
