package com.auth.application.usecase;

import com.auth.application.dto.LoginRequest;
import com.auth.application.dto.LoginResponse;
import com.auth.domain.exception.AuthException;
import com.auth.domain.exception.UserNotFoundException;
import com.auth.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.auth.domain.ports.input.LoginUseCase;
import com.auth.domain.ports.output.JwtProviderPort;
import com.auth.domain.ports.output.PasswordEncoderPort;
import com.auth.domain.ports.output.UserRepositoryPort;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final JwtProviderPort jwtProviderPort;

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepositoryPort
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        boolean validPassword =
                passwordEncoderPort.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!validPassword) {
            throw new AuthException("Invalid credentials");
        }

        if (Boolean.TRUE.equals(user.getMfaEnabled())) {

            return LoginResponse.builder()
                    .mfaRequired(true)
                    .email(user.getEmail())
                    .build();
        }

        return LoginResponse.builder()
                .accessToken(
                        jwtProviderPort.generateAccessToken(
                                user.getEmail()
                        )
                )
                .refreshToken(
                        jwtProviderPort.generateRefreshToken(
                                user.getEmail()
                        )
                )
                .mfaRequired(false)
                .build();
    }
}