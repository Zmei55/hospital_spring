package com.hospital_spring.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hospital_spring.security.utils.AuthorizationHeaderUtil;
import com.hospital_spring.security.utils.JwtUtil;
import com.hospital_spring.users.model.User;
import com.hospital_spring.users.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.hospital_spring.security.filter.JwtAuthenticationFilter.SIGN_IN_URL;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter { // срабатывает один раз (один запрос) в рамках одного треда(запроса)
    private final AuthorizationHeaderUtil authorizationHeaderUtil;
    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;

    // перекладывает данные пользователя из токена в контекст спринга
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().equals(SIGN_IN_URL)) { // если запрос пришёл на "/api/auth/login" урл, то ничего не делаем и пропускаем его дальше
            filterChain.doFilter(request, response);
        } else {
            if (authorizationHeaderUtil.hasAuthorizationToken(request)) { // если токен есть в спринге и в БД он не пуст
                String jwt = authorizationHeaderUtil.getToken(request); // получили токен из заголовка

                try {
                    Authentication authentication = jwtUtil.buildAuthentication(jwt); // объект текущей аутентификации
                    SecurityContextHolder.getContext().setAuthentication(authentication); // добавляем в держатель контекста

                    String username = SecurityContextHolder.getContext().getAuthentication().getName();
                    User user = usersRepository.findByUsername(username).orElseThrow(
                        () -> new UsernameNotFoundException("User not found")
                    );
                    String tokenFromDB = user.getToken();

                    if (!tokenFromDB.isEmpty() && Objects.equals(jwt, tokenFromDB)) {
                        filterChain.doFilter(request, response);
                    } else {
                        throw new JWTVerificationException("Token is incorrect");
                    }
                } catch (JWTVerificationException exception) {
                    logger.info(exception.getMessage());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else { // если токена нет, то отправляем дальше, но spring security не пропустит
                filterChain.doFilter(request, response);
            }
        }
    }
}
