package com.hospital_spring.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital_spring.security.config.details.CustomLogoutHandler;
import com.hospital_spring.security.config.details.UserDetailsServiceImpl;
import com.hospital_spring.security.filter.JwtAuthenticationFilter;
import com.hospital_spring.security.filter.JwtAuthorizationFilter;
import com.hospital_spring.shared.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;

import static com.hospital_spring.security.filter.JwtAuthenticationFilter.*;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
@RequiredArgsConstructor
public class TokenSecurityConfig {
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final CustomLogoutHandler logoutHandler;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity httpSecurity,
        JwtAuthenticationFilter jwtAuthenticationFilter,
        JwtAuthorizationFilter jwtAuthorizationFilter
    ) throws Exception {
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // отключает сессии (куки)
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.cors();

        httpSecurity.authorizeRequests()
            .antMatchers("/swagger-ui/**").permitAll()
            .antMatchers("/v3/api-docs/**").permitAll()
            .antMatchers(HttpMethod.POST, SIGN_UP_URL).not().fullyAuthenticated() //Доступ только POST для не зарегистрированных пользователей
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .defaultAuthenticationEntryPointFor((
                    (request, response, authException) ->
                        fillResponse(response, HttpStatus.UNAUTHORIZED, "User unauthorized")),
                new AntPathRequestMatcher("/api/**")
            )
            .and()
            .formLogin()
            .loginPage(SIGN_IN_URL)
            .failureHandler((
                (request, response, exception) ->
                    fillResponse(response, HttpStatus.UNAUTHORIZED, "Incorrect password or username")))
            .permitAll()
            .and()
            .logout()
            .logoutUrl(SIGN_OUT_URL)
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
            .permitAll();

        httpSecurity.addFilter(jwtAuthenticationFilter);
        httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class); // кладёт фильтр перед UsernamePassword...(самый первый)

        return httpSecurity.build();
    }

    @Autowired
    public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder); // token
    }

    // ручное заполнение ответа
    private void fillResponse(HttpServletResponse response, HttpStatus status, String message) {
        try {
            response.setStatus(status.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ResponseDto responseDto = ResponseDto.builder()
                .status(status.value())
                .message(message)
                .build();

            String body = objectMapper.writeValueAsString(responseDto);

            response.getWriter().write(body);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
