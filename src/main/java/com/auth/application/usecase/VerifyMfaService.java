package com.auth.application.usecase;

import com.auth.application.dto.MfaVerifyRequest;
import com.auth.application.dto.MfaVerifyResponse;
import com.auth.domain.exception.InvalidOtpException;
import com.auth.domain.ports.input.VerifyMfaUseCase;
import com.auth.domain.ports.output.JwtProviderPort;
import com.auth.domain.ports.output.MfaProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.auth.domain.exception.UserNotFoundException;
import com.auth.domain.model.User;
import com.auth.domain.ports.output.UserRepositoryPort;

@Service
@RequiredArgsConstructor
public class VerifyMfaService implements VerifyMfaUseCase {

    private final MfaProviderPort mfaProviderPort;
    private final JwtProviderPort jwtProviderPort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public MfaVerifyResponse verify(
            MfaVerifyRequest request
    ) {

        User user =
                userRepositoryPort
                        .findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found"
                                ));

        boolean valid =
                mfaProviderPort.validateOtp(
                        user.getMfaSecret(),
                        request.getOtpCode()
                );

        if (!valid) {
            throw new InvalidOtpException(
                    "Invalid OTP"
            );
        }

        return MfaVerifyResponse.builder()
                .accessToken(
                        jwtProviderPort.generateAccessToken(
                                user
                        )
                )
                .refreshToken(
                        jwtProviderPort.generateRefreshToken(
                                user.getEmail()
                        )
                )
                .build();
    }
}