package com.auth.domain.ports.output;

import com.auth.domain.model.User;

public interface JwtProviderPort {

    String generateAccessToken(User user);

    String generateRefreshToken(String subject);
}