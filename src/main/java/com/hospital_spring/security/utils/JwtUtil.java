package com.hospital_spring.security.utils;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hospital_spring.users.dto.UserForTokenDto;
import org.springframework.security.core.Authentication;

public interface JwtUtil {
    String generateToken(UserForTokenDto user, String authority, String issuer);

    Authentication buildAuthentication(String token) throws JWTVerificationException;
}
