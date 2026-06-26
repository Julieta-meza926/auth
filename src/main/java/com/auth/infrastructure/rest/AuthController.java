package com.auth.infrastructure.rest;

import com.auth.application.dto.*;
import com.auth.application.usecase.RegisterOtpUseCase;
import com.auth.domain.ports.input.LoginUseCase;
import com.auth.domain.ports.input.VerifyMfaUseCase;
import com.auth.infrastructure.persistence.mongo.UserDocument;
import com.auth.infrastructure.persistence.mongo.UserRepository;
import com.auth.infrastructure.security.BCryptPasswordAdapter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final VerifyMfaUseCase verifyMfaUseCase;
    private final BCryptPasswordAdapter passwordAdapter;
    private final UserRepository repository;
    private final RegisterOtpUseCase registerOtpUseCase;

    @GetMapping("/users")
    public List<UserDocument> users() {
        return repository.findAll();
    }

    @PostMapping("/test/users")
    public UserDocument createUser(@Valid
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

        return repository.save(user);
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

    @PostMapping("/register-otp")
    public ResponseEntity<RegisterOtpResponse> registerOtp(@Valid
            @RequestBody RegisterOtpRequest request) {

        String secret = registerOtpUseCase.execute(request);

        return ResponseEntity.ok(
                new RegisterOtpResponse(secret)
        );
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable String id
    ) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.ok("User deleted successfully");
    }
}