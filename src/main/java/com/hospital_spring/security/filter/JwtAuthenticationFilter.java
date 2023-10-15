package com.hospital_spring.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital_spring.security.authentication.RefreshTokenAuthentication;
import com.hospital_spring.security.config.details.AuthenticatedUser;
import com.hospital_spring.security.utils.AuthorizationHeaderUtil;
import com.hospital_spring.security.utils.JwtUtil;
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
import java.util.Map;

// выдаёт токен
@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final AuthorizationHeaderUtil authorizationHeaderUtil;
    private final ObjectMapper objectMapper;
    public static final String SIGN_UP_URL = "/api/auth/register";
    public static final String SIGN_IN_URL = "/api/auth/login";
    public static final String SIGN_OUT_URL = "/api/auth/logout";

    // конструктор класса фильтра
    public JwtAuthenticationFilter(
        AuthenticationConfiguration authenticationConfiguration,
        JwtUtil jwtUtil,
        AuthorizationHeaderUtil authorizationHeaderUtil,
        ObjectMapper objectMapper
    ) throws Exception {
        super(authenticationConfiguration.getAuthenticationManager());
        this.jwtUtil = jwtUtil;
        this.authorizationHeaderUtil = authorizationHeaderUtil;
        this.setFilterProcessesUrl(SIGN_IN_URL);
        this.objectMapper = objectMapper;
    }

    // пытается аутентифицировать пользователя
    // "username" и "password" достаёт самостоятельно
    // для обновления токена
    // если у spring security нет токена, тогда использует по умолчанию "username" и "password"
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (authorizationHeaderUtil.hasAuthorizationToken(request)) {
            String refreshToken = authorizationHeaderUtil.getToken(request); // получаем токен из запроса
            RefreshTokenAuthentication authentication = new RefreshTokenAuthentication(refreshToken); // создаём объект аутентикации
            return super.getAuthenticationManager().authenticate(authentication); // передаём spring security объект аутентикации
        } else { // если заголовка нет, то передаём для обычной работы
            return super.attemptAuthentication(request, response);
        }
    }

    // при положительной аутентификации отправлять accessToken
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.setContentType("application/json");

        GrantedAuthority currentAuthority = authResult.getAuthorities().stream().findFirst().orElseThrow();
        String username = ((AuthenticatedUser)authResult.getPrincipal()).getUsername();
        String issuer = request.getRequestURL().toString();

        Map<String, String> tokens = jwtUtil.generateTokens(username, currentAuthority.getAuthority(), issuer);

        objectMapper.writeValue(response.getOutputStream(), tokens);
    }

    // при отрицательной аутентификации отправлять ошибку 401
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
