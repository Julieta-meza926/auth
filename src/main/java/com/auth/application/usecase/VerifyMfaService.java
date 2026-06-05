package com.auth.application.usecase;

import com.auth.application.dto.MfaVerifyRequest;
import com.auth.application.dto.MfaVerifyResponse;
import com.auth.domain.exception.InvalidOtpException;
import com.auth.domain.ports.input.VerifyMfaUseCase;
import com.auth.domain.ports.output.JwtProviderPort;
import com.auth.domain.ports.output.MfaProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyMfaService
        implements VerifyMfaUseCase {

    private final MfaProviderPort mfaProviderPort;
    private final JwtProviderPort jwtProviderPort;

    @Override
    public MfaVerifyResponse verify(
            MfaVerifyRequest request
    ) {

        boolean valid =
                mfaProviderPort.validateOtp(
                        request.getMfaToken(),
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
                                request.getMfaToken()
                        )
                )
                .refreshToken(
                        jwtProviderPort.generateRefreshToken(
                                request.getMfaToken()
                        )
                )
                .build();
    }
}
