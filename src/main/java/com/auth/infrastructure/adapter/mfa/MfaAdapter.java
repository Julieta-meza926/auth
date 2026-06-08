package com.auth.infrastructure.adapter.mfa;

import com.auth.domain.ports.output.MfaProviderPort;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.stereotype.Component;

@Component
public class MfaAdapter implements MfaProviderPort {

    private final GoogleAuthenticator googleAuth = new GoogleAuthenticator();

    @Override
    public String generateMfaToken(String email) {

        GoogleAuthenticatorKey key = googleAuth.createCredentials();

        return key.getKey();
    }

    @Override
    public boolean validateOtp(String secret, String otpCode) {

        int code = Integer.parseInt(otpCode);

        return googleAuth.authorize(secret, code);
    }
}