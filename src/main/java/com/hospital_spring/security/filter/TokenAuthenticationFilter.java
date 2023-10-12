package com.hospital_spring.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital_spring.users.model.Token;
import com.hospital_spring.users.repositories.TokensRepository;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

// выдаёт токен
@Component
public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final TokensRepository tokensRepository;
    private final ObjectMapper objectMapper;
    public static final String USERNAME_PARAMETER = "email"; // по умолч - "username"
    public static final String AUTHENTICATION_URL = "/auth/token";

    // вытаскиваем "email", а не "username"
    public TokenAuthenticationFilter(
        AuthenticationConfiguration authenticationConfiguration,
        TokensRepository tokensRepository,
        ObjectMapper objectMapper
    ) throws Exception {
        super(authenticationConfiguration.getAuthenticationManager());
        this.tokensRepository = tokensRepository;
        this.setUsernameParameter(USERNAME_PARAMETER);
        this.setFilterProcessesUrl(AUTHENTICATION_URL);
        this.objectMapper = objectMapper;
    }

    // пытается аутентифицировать пользователя
    // "username" и "password" достаёт самостоятельно
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        return super.attemptAuthentication(request, response);
//    }

    // при положит аутент выдать токен(любую строку)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.setContentType("application/json");

        String generatedToken = UUID.randomUUID().toString();

        Map<String, String> token = Collections.singletonMap("token", generatedToken);

        objectMapper.writeValue(response.getOutputStream(), token);

        GrantedAuthority currentAuthority = authResult.getAuthorities().stream().findFirst().orElseThrow();

        tokensRepository.save(
            Token.builder()
                .token(generatedToken)
                .email(authResult.getName())
                .authority(currentAuthority.getAuthority())
                .build()
        );
    }

    // при отриц аутент отпр ошибку 401
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
