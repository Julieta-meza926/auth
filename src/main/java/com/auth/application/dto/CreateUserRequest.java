package com.auth.application.dto;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String email;
    private String name;
    private String password;
    private Boolean mfaEnabled;
}
