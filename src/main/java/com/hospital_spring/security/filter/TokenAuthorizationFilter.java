package com.hospital_spring.security.filter;

import com.hospital_spring.security.config.details.AuthenticatedUser;
import com.hospital_spring.security.utils.AuthorizationHeaderUtil;
import com.hospital_spring.users.model.Token;
import com.hospital_spring.users.model.User;
import com.hospital_spring.users.repositories.TokensRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.hospital_spring.security.filter.TokenAuthenticationFilter.AUTHENTICATION_URL;

@Component
@RequiredArgsConstructor
public class TokenAuthorizationFilter extends OncePerRequestFilter { // срабатывает один раз (один запрос) в рамках одного треда(запроса)
    private final AuthorizationHeaderUtil authorizationHeaderUtil;
    private final TokensRepository tokensRepository;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().equals(AUTHENTICATION_URL)) { // если запрос пришёл на ... урл, то ничего не делаем и пропускаем его дальше
            filterChain.doFilter(request, response);
        } else {
            if (authorizationHeaderUtil.hasAuthorizationToken(request)) { // если токен есть
                String token = authorizationHeaderUtil.getToken(request); // получили токен из заголовка

                try {
                    Token userToken = tokensRepository.findFirstByToken(token).orElseThrow();
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                        new AuthenticatedUser(
                            User.builder()
                                .email(userToken.getEmail())
                                .role(User.Role.valueOf(userToken.getAuthority()))
                                .build()
                        ),
                        null,
                        Collections.singleton(new SimpleGrantedAuthority(userToken.getAuthority()))
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                } catch (Exception exception) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }

            } else { // если токена нет, то отправляем дальше, но spring security не пропустит
                filterChain.doFilter(request, response);
            }
        }
    }
}
