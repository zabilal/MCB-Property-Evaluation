package com.mcbproperty.authservice.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


@Service
@Configuration
@Slf4j
public class JwtUtil {
    private static final String JWT_SECRET = "$2a$10$8XT9BkaViS9c4TqSc6N8Bu/P1ICLXLZGN/pxDh/gngw6wEGgY0nGi";

    @Value("${mcb.auth.jwtSecret}")
    private String jwtSecret;
    public static String generateJwt (String subject, String roles) {
        return Jwts
                .builder()
                .setIssuer("MCB")
                .setSubject(subject)
                .setAudience("MCB")
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                .setIssuedAt(new Date())
                .setId(UUID.randomUUID().toString())
                .claim("authorities", roles)
                .signWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes()))
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid token signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("token claims is empty: {}", e.getMessage());
        }

        return false;
    }

}
