package com.hospital_spring.security.utils;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.core.Authentication;

public interface JwtUtil {
    String generateToken(String subject, String authority, String issuer);

    Authentication buildAuthentication(String token) throws JWTVerificationException;
}
