package com.auth.application.usecase;

import com.auth.application.dto.LoginRequest;
import com.auth.application.dto.LoginResponse;
import com.auth.domain.ports.input.LoginUseCase;
import com.auth.domain.ports.output.AuthenticationProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final AuthenticationProviderPort authenticationProviderPort;

    @Override
    public LoginResponse login(LoginRequest request) {

        return authenticationProviderPort.authenticate(request);

    }
}