package com.auth.infrastructure.rest;

import com.auth.application.dto.*;
import com.auth.domain.ports.input.LoginUseCase;
import com.auth.domain.ports.input.VerifyMfaUseCase;
import com.auth.infrastructure.persistence.mongo.UserDocument;
import com.auth.infrastructure.persistence.mongo.UserRepository;
import com.auth.infrastructure.security.BCryptPasswordAdapter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final VerifyMfaUseCase verifyMfaUseCase;
    private final UserRepository userRepository;
    private final BCryptPasswordAdapter passwordAdapter;
    private final UserRepository repository;

    @GetMapping("/users")
    public List<UserDocument> users() {
        return repository.findAll();
    }

    @PostMapping("/test/users")
    public UserDocument createUser(
            @RequestBody CreateUserRequest request) {

        UserDocument user = new UserDocument();

        user.setEmail(request.getEmail());
        user.setName(request.getName());

        user.setPassword(
                passwordAdapter.encode(
                        request.getPassword()
                )
        );

        user.setMfaEnabled(
                request.getMfaEnabled()
        );

        return userRepository.save(user);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        return loginUseCase.login(request);
    }
    @PostMapping("/mfa/verify")
    public MfaVerifyResponse verifyMfa(
            @Valid
            @RequestBody
            MfaVerifyRequest request
    ) {

        return verifyMfaUseCase.verify(
                request
        );

    }
}