package com.auth.infrastructure.persistence.mongo;

import com.auth.domain.model.User;
import com.auth.domain.ports.output.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository repository;

    @Override
    public Optional<User> findByEmail(String email) {

        return repository.findByEmail(email)
                .map(this::toDomain);
    }

    private User toDomain(UserDocument document) {

        return User.builder()
                .id(document.getId())
                .email(document.getEmail())
                .name(document.getName())
                .password(document.getPassword())
                .mfaEnabled(document.getMfaEnabled())
                .build();
    }
}