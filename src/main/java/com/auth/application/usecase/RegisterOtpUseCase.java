package com.auth.application.usecase;

import com.auth.application.dto.RegisterOtpRequest;
import com.auth.domain.model.User;
import com.auth.domain.ports.output.PasswordEncoderPort;
import com.auth.domain.ports.output.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterOtpUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoder;
    private final MfaUseCase mfaService;

    public String execute(RegisterOtpRequest request) {

        User user = userRepositoryPort.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BadCredentialsException("Usuario no encontrado"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new BadCredentialsException(
                    "Credenciales inválidas");
        }

        if (Boolean.TRUE.equals(user.getMfaEnabled())) {
            throw new IllegalStateException(
                    "MFA ya se encuentra habilitado");
        }

        String secret = mfaService.generateSecret();

        user.setMfaEnabled(true);
        user.setMfaSecret(secret);

        userRepositoryPort.save(user);

        return secret;
    }
}