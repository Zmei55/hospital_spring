package com.hospital_spring.security.provider;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hospital_spring.security.authentication.RefreshTokenAuthentication;
import com.hospital_spring.security.exceptions.RefreshTokenException;
import com.hospital_spring.security.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {
    private final JwtUtil jwtUtil;

    // аутентифицируем
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String refreshTokenValue = (String) authentication.getCredentials();

        try {
            return jwtUtil.buildAuthentication(refreshTokenValue);
        } catch (JWTVerificationException exception) {
            log.info(exception.getMessage());
            throw new RefreshTokenException(exception.getMessage(), exception);
        }
    }

    // проверяет refreshToken
    @Override
    public boolean supports(Class<?> authentication) {
        return RefreshTokenAuthentication.class.isAssignableFrom(authentication);
    }
}
