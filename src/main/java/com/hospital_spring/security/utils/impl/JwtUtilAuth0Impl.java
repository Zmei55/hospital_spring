package com.hospital_spring.security.utils.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hospital_spring.security.config.details.AuthenticatedUser;
import com.hospital_spring.security.utils.JwtUtil;
import com.hospital_spring.users.dto.UserForTokenDto;
import com.hospital_spring.users.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;

@Component
public class JwtUtilAuth0Impl implements JwtUtil {
//    private static final long ACCESS_TOKEN_EXPIRES_TIME = 60 * 1000; // one minute
    private static final long ACCESS_TOKEN_EXPIRES_TIME = 20 * 60 * 1000; // three minutes

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String generateToken(UserForTokenDto user, String authority, String issuer) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8)); // алгоритм создания токена

        return JWT.create()
            .withSubject(user.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRES_TIME)) // на какое время выдан accessToken
            .withClaim("id", user.getId())
            .withClaim("name", user.getName())
            .withClaim("workplace", authority)
            .withClaim("position", user.getPosition())
            .withClaim("isNotLocked", user.isNotLocked())
            .withIssuer(issuer)
            .sign(algorithm);
    }

    // 1. имплементирует метод из интерфейса
    @Override
    public Authentication buildAuthentication(String token) throws JWTVerificationException {
        // 4. собираем объект аутентификации
        ParsedToken parsedToken = parse(token);

        UserDetails userDetails = new AuthenticatedUser(
            User.builder()
                .id(parsedToken.getId())
                .username(parsedToken.getUsername())
                .name(parsedToken.getName())
                .workplace(User.Workplace.valueOf(parsedToken.getWorkplace()))
                .position(parsedToken.getPosition())
                .isNotLocked(parsedToken.isNotLocked())
                .build()
        );

        return new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            Collections.singleton(new SimpleGrantedAuthority(parsedToken.getWorkplace()))
        );
    }

    // 3. метод для обработки метода
    private ParsedToken parse(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));

        JWTVerifier verifier = JWT.require(algorithm).build(); // верификация токена
        DecodedJWT decodedJWT = verifier.verify(token); // декодирование токена

        String username = decodedJWT.getSubject();
        Long id = decodedJWT.getClaim("id").asLong();
        String name = decodedJWT.getClaim("name").asString();
        String workplace = decodedJWT.getClaim("workplace").asString();
        String position = decodedJWT.getClaim("position").asString();
        boolean isNotLocked = decodedJWT.getClaim("isNotLocked").asBoolean();

        return ParsedToken.builder()
            .id(id)
            .username(username)
            .name(name)
            .workplace(workplace)
            .position(position)
            .isNotLocked(isNotLocked)
            .build();
    }

    // 2. создали класс для обработки токена (вытаскивать необходимую информацию)
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    private static class ParsedToken {
        private Long id;
        private String username;
        private String name;
        private String workplace;
        private String position;
        private boolean isNotLocked;
    }
}
