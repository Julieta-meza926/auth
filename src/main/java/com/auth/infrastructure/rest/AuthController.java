package com.auth.infrastructure.rest;

import com.auth.application.dto.LoginRequest;
import com.auth.application.dto.LoginResponse;
import com.auth.domain.ports.input.LoginUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        return loginUseCase.login(request);
    }
}