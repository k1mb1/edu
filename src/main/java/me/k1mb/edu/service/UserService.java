package me.k1mb.edu.service;

import lombok.NonNull;
import me.k1mb.edu.model.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public interface UserService {
    User getById(@NonNull UUID id);

    User create(@NonNull User user);

    User createFromJwt(@NonNull Jwt jwt);
}
