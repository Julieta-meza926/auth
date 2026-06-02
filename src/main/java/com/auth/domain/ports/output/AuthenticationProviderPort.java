package com.auth.domain.ports.output;

import com.auth.application.dto.LoginRequest;
import com.auth.application.dto.LoginResponse;

public interface AuthenticationProviderPort {

    LoginResponse authenticate(LoginRequest request);

}
