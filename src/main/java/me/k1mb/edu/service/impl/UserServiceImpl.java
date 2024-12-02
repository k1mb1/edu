package me.k1mb.edu.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import me.k1mb.edu.exeption.ResourceNotFoundException;
import me.k1mb.edu.model.User;
import me.k1mb.edu.repository.UserRepository;
import me.k1mb.edu.service.UserService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
class UserServiceImpl implements UserService {
    final UserRepository userRepository;

    public User getById(@NonNull final UUID id) {
        return userRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found %s".formatted(id)));
    }

    public User create(@NonNull final User user) {
        return userRepository.save(user);
    }

    public User createFromJwt(@NonNull final Jwt jwt) {
        val id = UUID.fromString(jwt.getClaim("sub"));
        if (userRepository.existsById(id)) {
            return getById(id);// TODO: registration
        }

        return create(User.builder()
            .id(id)
            .username(jwt.getClaim("name"))
            .email(jwt.getClaim("preferred_username"))
            .build());
    }
}
