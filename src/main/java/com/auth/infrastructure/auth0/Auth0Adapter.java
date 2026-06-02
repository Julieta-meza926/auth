package com.auth.infrastructure.auth0;

import com.auth.application.dto.LoginRequest;
import com.auth.application.dto.LoginResponse;
import com.auth.domain.ports.output.AuthenticationProviderPort;
import org.springframework.stereotype.Component;

@Component
public class Auth0Adapter implements AuthenticationProviderPort {

    @Override
    public LoginResponse authenticate(LoginRequest request) {

        return LoginResponse.builder()
                .accessToken("fake-jwt-token")
                .mfaRequired(false)
                .build();
    }
}