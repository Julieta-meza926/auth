package com.auth.infrastructure.security;

import com.auth.domain.ports.output.PasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordAdapter
        implements PasswordEncoderPort {

    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public boolean matches(
            String rawPassword,
            String encodedPassword) {

        return encoder.matches(
                rawPassword,
                encodedPassword
        );
    }

    public String encode(
            String password) {

        return encoder.encode(
                password
        );
    }

}
