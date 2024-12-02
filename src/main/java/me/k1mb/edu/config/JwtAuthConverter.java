package me.k1mb.edu.config;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import me.k1mb.edu.service.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toUnmodifiableSet;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    UserService userService;

    @Override
    public AbstractAuthenticationToken convert(@NonNull final Jwt jwt) {
        val authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream())
            .collect(toSet());

        val user = userService.createFromJwt(jwt);
        // temporary registration user for association not in keycloak -> TODO: registration and authorization

        return new JwtAuthenticationToken(jwt, authorities, jwt.getSubject());
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(@NonNull final Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null) {
            return Set.of();
        }

        var resource = (Map<String, List<String>>) resourceAccess.get("edu-client");

        if (resource == null) {
            return Set.of();
        }
        return resource.get("roles").stream()
            .map(SimpleGrantedAuthority::new)
            .collect(toUnmodifiableSet());
    }
}
