package me.k1mb.edu.service;

import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.k1mb.edu.exeption.ResourceNotFoundException;
import me.k1mb.edu.model.User;
import me.k1mb.edu.repository.UserRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public User getById(UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found %s".formatted(id)));
    }

    public User create(User user) {
        return userRepository.saveAndFlush(user);
    }

    public User createFromJwt(Jwt jwt) {
        User user = new User();
        UUID id = UUID.fromString(jwt.getClaim("sub"));
        user.setId(id);
        if (userRepository.existsById(id)) {
            return getById(id);
        }
        user.setUsername(jwt.getClaim("name"));
        user.setEmail(jwt.getClaim("preferred_username"));

        return create(user);
    }
}
