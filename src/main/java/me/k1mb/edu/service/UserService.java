package me.k1mb.edu.service;

import lombok.NonNull;
import me.k1mb.edu.repository.entity.UserEntity;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public interface UserService {
    UserEntity getById(@NonNull UUID id);

    UserEntity create(@NonNull UserEntity userEntity);

    UserEntity createFromJwt(@NonNull Jwt jwt);
}
