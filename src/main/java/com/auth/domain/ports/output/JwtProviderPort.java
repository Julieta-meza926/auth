package com.auth.domain.ports.output;

public interface JwtProviderPort {

    String generateAccessToken(
            String subject
    );

    String generateRefreshToken(
            String subject
    );

}
