package com.auth.infrastructure.adapter.auth;

import com.auth.application.dto.LoginRequest;
import com.auth.application.dto.LoginResponse;
import com.auth.domain.exception.AuthException;
import com.auth.domain.ports.output.AuthenticationProviderPort;
import com.auth.infrastructure.adapter.auth.dto.AuthTokenRequest;
import com.auth.infrastructure.adapter.auth.dto.AuthTokenResponse;
import com.auth.infrastructure.config.Auth0Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class Auth0AuthenticationAdapter implements AuthenticationProviderPort {

    private final WebClient webClient;
    private final Auth0Properties auth0Properties;

    @Override
    public LoginResponse authenticate(LoginRequest request) {

        try {

            AuthTokenRequest authRequest =
                    AuthTokenRequest.builder()
                            .grant_type("password")
                            .username(request.getEmail())
                            .password(request.getPassword())
                            .audience(auth0Properties.getAudience())
                            .client_id(auth0Properties.getClientId())
                            .client_secret(auth0Properties.getClientSecret())
                            .scope("openid profile email offline_access")
                            .build();

            AuthTokenResponse response =
                    webClient.post()
                            .uri("https://" +
                                    auth0Properties.getDomain() +
                                    "/oauth/token")
                            .bodyValue(authRequest)
                            .retrieve()
                            .bodyToMono(AuthTokenResponse.class)
                            .block();

            return LoginResponse.builder()
                    .accessToken(response.getAccess_token())
                    .refreshToken(response.getRefresh_token())
                    .mfaRequired(false)
                    .build();

        } catch (Exception ex) {

            throw new AuthException(
                    "Invalid credentials"
            );

        }

    }

}
