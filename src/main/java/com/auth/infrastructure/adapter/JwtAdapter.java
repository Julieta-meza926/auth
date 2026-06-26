package com.auth.infrastructure.adapter;

import com.auth.domain.ports.output.JwtProviderPort;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtAdapter
        implements JwtProviderPort {

    private static final String SECRET =
            "my-super-secret-key-my-super-secret-key-123456";

    @Override
    public String generateAccessToken(
            String subject
    ) {

        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 15
                        )
                )
                .signWith(
                        Keys.hmacShaKeyFor(
                                SECRET.getBytes()
                        )
                )
                .compact();
    }

    @Override
    public String generateRefreshToken(
            String subject
    ) {

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
                                SECRET.getBytes()
                        )
                )
                .compact();
    }
}