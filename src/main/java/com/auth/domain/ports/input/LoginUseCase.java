package com.auth.domain.ports.input;

import com.auth.application.dto.LoginRequest;
import com.auth.application.dto.LoginResponse;

public interface LoginUseCase {

    LoginResponse login(LoginRequest request);

}