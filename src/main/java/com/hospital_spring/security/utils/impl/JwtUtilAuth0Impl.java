package com.hospital_spring.security.utils.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hospital_spring.security.config.details.AuthenticatedUser;
import com.hospital_spring.security.utils.JwtUtil;
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
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtilAuth0Impl implements JwtUtil {
    private static final long ACCESS_TOKEN_EXPIRES_TIME = 1 * 60 * 1000; // one minute
    private static final long REFRESH_TOKEN_EXPIRES_TIME = 3 * 60 * 1000; // three minutes

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Map<String, String> generateTokens(String subject, String authority, String issuer) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8)); // алгоритм создания токена

        String accessToken = JWT.create()
            .withSubject(subject)
            .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRES_TIME)) // на какое время выдан токен
            .withClaim("role", authority)
            .withIssuer(issuer)
            .sign(algorithm);

        String refreshToken = JWT.create()
            .withSubject(subject)
            .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRES_TIME)) // на какое время выдан токен
            .withClaim("role", authority)
            .withIssuer(issuer)
            .sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    // 1. импл метод из интерф
    @Override
    public Authentication buildAuthentication(String token) throws JWTVerificationException {
        // 4. собираем объект аутентификации
        ParsedToken parsedToken = parse(token);

        UserDetails userDetails = new AuthenticatedUser(
            User.builder()
                .email(parsedToken.getEmail())
                .role(User.Role.valueOf(parsedToken.getRole()))
                .build()
        );

        return new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            Collections.singleton(new SimpleGrantedAuthority(parsedToken.getRole()))
        );
    }

    // 3. метод для обработки метода
    private ParsedToken parse(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));

        JWTVerifier verifier = JWT.require(algorithm).build(); // верификация токена
        DecodedJWT decodedJWT = verifier.verify(token); // декодирование токена

        String email = decodedJWT.getSubject(); // вытаскиваем email из токена
        String role = decodedJWT.getClaim("role").asString(); // вытаскиваем role из токена

        return ParsedToken.builder()
            .email(email)
            .role(role)
            .build();
    }

    // 2. создали класс для обработки токена (вытаскивать необходимую информацию)
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    private static class ParsedToken {
        private String email;
        private String role;
    }
}
