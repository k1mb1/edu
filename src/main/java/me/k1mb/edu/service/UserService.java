package me.k1mb.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import me.k1mb.edu.exeption.ResourceNotFoundException;
import me.k1mb.edu.model.User;
import me.k1mb.edu.repository.UserRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@FieldDefaults(makeFinal = true)
public class UserService {
    UserRepository userRepository;

    public User getById(UUID id) {
        return userRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found %s".formatted(id)));
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User createFromJwt(Jwt jwt) {
        var user = new User();
        val id = UUID.fromString(jwt.getClaim("sub"));
        if (userRepository.existsById(id)) {
            return getById(id);
        }
        user.setId(id);
        user.setUsername(jwt.getClaim("name"));
        user.setEmail(jwt.getClaim("preferred_username"));

        return create(user);
    }
}
