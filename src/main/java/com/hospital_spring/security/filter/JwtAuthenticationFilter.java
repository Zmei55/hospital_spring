package com.hospital_spring.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital_spring.security.config.details.AuthenticatedUser;
import com.hospital_spring.security.utils.JwtUtil;
import com.hospital_spring.shared.dto.ResponseDto;
import com.hospital_spring.users.dto.UserAndTokenResponseDto;
import com.hospital_spring.users.dto.UserForTokenDto;
import com.hospital_spring.users.model.User;
import com.hospital_spring.users.repositories.UsersRepository;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// выдаёт токен
@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;
    private final ObjectMapper objectMapper;
    public static final String SIGN_UP_URL = "/api/auth/register";
    public static final String SIGN_IN_URL = "/api/auth/login";
    public static final String SIGN_OUT_URL = "/api/auth/logout";

    // конструктор класса фильтра
    public JwtAuthenticationFilter(
        AuthenticationConfiguration authenticationConfiguration,
        JwtUtil jwtUtil,
        UsersRepository usersRepository,
        ObjectMapper objectMapper
    ) throws Exception {
        super(authenticationConfiguration.getAuthenticationManager());
        this.jwtUtil = jwtUtil;
        this.usersRepository = usersRepository;
        this.setFilterProcessesUrl(SIGN_IN_URL);
        this.objectMapper = objectMapper;
    }

    // при положительной аутентификации отправлять accessToken
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        response.setContentType("application/json");

        GrantedAuthority currentAuthority = authResult.getAuthorities().stream().findFirst().orElseThrow();
        Long userId = ((AuthenticatedUser) authResult.getPrincipal()).getUser().getId();
        String issuer = request.getRequestURL().toString();

        User user = usersRepository.findById(userId).orElseThrow(
            () -> new UsernameNotFoundException("User not found")
        );

        UserForTokenDto userForToken = UserForTokenDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .workplace(user.getWorkplace().name())
            .isNotLocked(user.isNotLocked())
            .build();

        String token = jwtUtil.generateToken(userForToken, currentAuthority.getAuthority(), issuer);

        user.setToken(token);
        usersRepository.save(user);

        ResponseDto responseDto = ResponseDto.builder()
            .message("Successful Authentication")
            .status(200)
            .data(UserAndTokenResponseDto.from(user, token))
            .build();

        objectMapper.writeValue(response.getOutputStream(), responseDto);
    }

    // при отрицательной аутентификации отправлять ошибку 401
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
