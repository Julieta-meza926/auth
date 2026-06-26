package com.auth.infrastructure.persistence.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@Data
public class UserDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String name;

    private String password;

    private Boolean mfaEnabled;

    private String mfaSecret;

}
