package com.auth.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "auth0")
public class Auth0Properties {

    private String domain;

    private String audience;

    private String clientId;

    private String clientSecret;

}
