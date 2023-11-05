package com.hospital_spring.security.config.details;

import com.hospital_spring.security.utils.AuthorizationHeaderUtil;
import com.hospital_spring.security.utils.JwtUtil;
import com.hospital_spring.users.model.User;
import com.hospital_spring.users.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final AuthorizationHeaderUtil authorizationHeaderUtil;
    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String jwt = authorizationHeaderUtil.getToken(request); // получили токен из заголовка
        Authentication authentication1 = jwtUtil.buildAuthentication(jwt); // объект текущей аутентификации
        SecurityContextHolder.getContext().setAuthentication(authentication1); // добавляем в держатель контекста

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = usersRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("User not found")
        );
        user.setToken(null);
        usersRepository.save(user);
    }
}
