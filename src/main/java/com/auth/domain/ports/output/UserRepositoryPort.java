package com.auth.domain.ports.output;

import com.auth.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {

    Optional<User> findByEmail(
            String email
    );

}
