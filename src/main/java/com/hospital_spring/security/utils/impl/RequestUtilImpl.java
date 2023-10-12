package com.hospital_spring.security.utils.impl;

import com.hospital_spring.security.utils.AuthorizationHeaderUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestUtilImpl implements AuthorizationHeaderUtil {
    private static final String AUTHORIZATION_HEADER_NAME = "AUTHORIZATION";
    private static final String BEARER = "Bearer ";

    @Override
    public boolean hasAuthorizationToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER_NAME);
        return header != null && header.startsWith(BEARER); // проверяет что такой заголовок есть
    }

    @Override
    public String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
        return authorizationHeader.substring(BEARER.length()); // вытаскивает этот заголовок
    }
}
