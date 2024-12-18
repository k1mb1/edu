package me.k1mb.edu.controller.config;

import lombok.RequiredArgsConstructor;
import me.k1mb.edu.controller.converter.JwtAuthConverter;
import me.k1mb.edu.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    static final String[] PUBLIC_ENDPOINTS = {"/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs"};

    final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http
            .cors(AbstractHttpConfigurer::disable)//TODO: Посмотреть подробнее
            .csrf(AbstractHttpConfigurer::disable)//TODO: Посмотреть подробнее
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(PUBLIC_ENDPOINTS)
                .permitAll()
                .anyRequest()
                .authenticated())
            .sessionManagement(
                sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS))
            .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(
                jwt -> jwt.jwtAuthenticationConverter(new JwtAuthConverter(userService))))
            .build();
    }
}
