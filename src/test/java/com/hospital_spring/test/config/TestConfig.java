package com.hospital_spring.test.config;

import com.hospital_spring.security.config.details.AuthenticatedUser;
import com.hospital_spring.users.enums.Position;
import com.hospital_spring.users.enums.Role;
import com.hospital_spring.users.enums.Workplace;
import com.hospital_spring.users.model.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
}) // убрать всё что связано с БД. БД не будет создаваться/подключаться автоматически
public class TestConfig {
    public static final String MOCK_AUTHENTICATED = "authenticated user";

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        // имитируем аутентифицированного пользователя
        return new InMemoryUserDetailsManager() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                if (username.equals(MOCK_AUTHENTICATED)) {
                    return new AuthenticatedUser(
                        User.builder()
                            .id(1L)
                            .username("asd")
                            .name("John Smith")
                            .email("john_smith@mail.com")
                            .role(Role.ADMIN)
                            .workplace(Workplace.TREATMENT_ROOM)
                            .position(Position.NURSE)
                            .token("lkdflsdm.sldfksld.jdfkjdfkj")
                            .build()
                    );
                } else throw new UsernameNotFoundException("User not found");
            }
        };
    }
}
