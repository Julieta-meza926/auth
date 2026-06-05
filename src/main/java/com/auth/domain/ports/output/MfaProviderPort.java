package com.auth.domain.ports.output;

public interface MfaProviderPort {

    String generateMfaToken(
            String email
    );

    boolean validateOtp(
            String mfaToken,
            String otpCode
    );

}
