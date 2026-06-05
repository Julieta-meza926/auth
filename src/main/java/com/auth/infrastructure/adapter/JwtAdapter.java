package com.auth.infrastructure.adapter;

import com.auth.domain.ports.output.JwtProviderPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtAdapter
        implements JwtProviderPort {

    @Override
    public String generateAccessToken(
            String subject
    ) {

        return "access-" +
                UUID.randomUUID();

    }

    @Override
    public String generateRefreshToken(
            String subject
    ) {

        return "refresh-" +
                UUID.randomUUID();

    }
}
