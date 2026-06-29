package com.auth.infrastructure.adapter;

import com.auth.domain.model.User;
import com.auth.domain.ports.output.JwtProviderPort;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtAdapter implements JwtProviderPort {

    private final String secret;

    public JwtAdapter(
            @Value("${jwt.secret}") String secret
    ) {
        this.secret = secret;
    }

    @Override
    public String generateAccessToken(User user) {

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 15
                        )
                )
                .signWith(
                        Keys.hmacShaKeyFor(
                                secret.getBytes()
                        )
                )
                .compact();
    }

    @Override
    public String generateRefreshToken(String subject) {

        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000L * 60 * 60 * 24 * 7
                        )
                )
                .signWith(
                        Keys.hmacShaKeyFor(
                                secret.getBytes()
                        )
                )
                .compact();
    }
}