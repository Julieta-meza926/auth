package com.auth.domain.ports.output;

public interface PasswordEncoderPort {

    boolean matches(
            String rawPassword,
            String encodedPassword
    );

}
