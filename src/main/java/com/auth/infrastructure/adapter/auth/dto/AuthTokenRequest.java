package com.auth.infrastructure.adapter.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenRequest {

    private String grant_type;

    private String username;

    private String password;

    private String audience;

    private String client_id;

    private String client_secret;

    private String scope;

}
