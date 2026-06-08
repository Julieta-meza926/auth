package com.auth.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String id;

    private String email;

    private String name;

    private String password;

    private Boolean mfaEnabled;

    private String mfaSecret;


}
