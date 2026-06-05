package com.auth.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MfaVerifyResponse {

    private String accessToken;

    private String refreshToken;

}
