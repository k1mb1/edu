package me.k1mb.edu.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import me.k1mb.edu.repository.UserRepository;
import me.k1mb.edu.repository.entity.UserEntity;
import me.k1mb.edu.service.UserService;
import me.k1mb.edu.service.exception.ResourceNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
class UserServiceImpl implements UserService {
    final UserRepository userRepository;

    public UserEntity getById(@NonNull final UUID id) {
        return userRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден %s".formatted(id)));
    }

    public UserEntity create(@NonNull final UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity createFromJwt(@NonNull final Jwt jwt) {
        val id = UUID.fromString(jwt.getClaim("sub"));
        if (userRepository.existsById(id)) {
            return getById(id);// TODO: временная регистрация пользователей в контексте приложения
        }

        return create(UserEntity.builder()
            .id(id)
            .email(jwt.getClaim("preferred_username"))
            .username(jwt.getClaim("name"))
            .build());
    }
}
