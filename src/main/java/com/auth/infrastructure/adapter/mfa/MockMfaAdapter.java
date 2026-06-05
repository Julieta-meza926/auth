package com.auth.infrastructure.adapter.mfa;

import com.auth.domain.ports.output.MfaProviderPort;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MockMfaAdapter
        implements MfaProviderPort {

    private final Map<String, String> otpStorage =
            new ConcurrentHashMap<>();

    @Override
    public String generateMfaToken(
            String email
    ) {

        String token =
                UUID.randomUUID().toString();

        otpStorage.put(
                token,
                "123456"
        );

        return token;
    }

    @Override
    public boolean validateOtp(
            String mfaToken,
            String otpCode
    ) {

        return otpCode.equals(
                otpStorage.get(mfaToken)
        );
    }
}
