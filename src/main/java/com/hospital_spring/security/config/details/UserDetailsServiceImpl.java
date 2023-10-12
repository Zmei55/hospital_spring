package com.hospital_spring.security.config.details;

import com.hospital_spring.users.model.User;
import com.hospital_spring.users.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("User <" + username + "> not found")
            );

        return new AuthenticatedUser(user);
    }
}
