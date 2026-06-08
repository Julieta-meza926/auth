package com.auth.application.dto;

import lombok.Data;

@Data
public class RegisterOtpRequest {

    private String email;

    private String password;
}
