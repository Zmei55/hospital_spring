package com.hospital_spring.security.config;

import com.hospital_spring.security.config.details.UserDetailsServiceImpl;
import com.hospital_spring.security.filter.TokenAuthenticationFilter;
import com.hospital_spring.security.filter.TokenAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@RequiredArgsConstructor
public class TokenSecurityConfig {
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity httpSecurity,
        TokenAuthenticationFilter tokenAuthenticationFilter,
        TokenAuthorizationFilter tokenAuthorizationFilter
    ) throws Exception {
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // отключает сессии (куки)
        httpSecurity.csrf().disable();

        httpSecurity.authorizeRequests()
            .antMatchers("/swagger-ui/**").permitAll()
            .antMatchers("/v3/api-docs/**").permitAll()
            .antMatchers("/api/auth/sign-up/**").permitAll()
//            .antMatchers("/auth/token").permitAll()
            .anyRequest().hasAuthority("ADMIN")
//            .and()
//            .formLogin()
//            .defaultSuccessUrl("/swagger-ui/index.html")
//            .and()
//            .exceptionHandling()
//            .defaultAuthenticationEntryPointFor(
//                new Http403ForbiddenEntryPoint(),
//                new AntPathRequestMatcher("/api/**")
//            )
        ;

        httpSecurity.addFilter(tokenAuthenticationFilter);
        httpSecurity.addFilterBefore(tokenAuthorizationFilter, UsernamePasswordAuthenticationFilter.class); // ложим фильтр перед юзерпасворд...(самый первый)

        return httpSecurity.build();
    }

    @Autowired
    public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }
}