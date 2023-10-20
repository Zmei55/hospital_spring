package com.hospital_spring.users.services.impl;

import com.hospital_spring.shared.exceptions.UserIsPresentException;
import com.hospital_spring.users.dto.ProfileDto;
import com.hospital_spring.users.model.User;
import com.hospital_spring.users.repositories.UsersRepository;
import com.hospital_spring.users.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ProfileDto signUp(
        String username,
        String password,
        String firstName,
        String lastName,
        String workplace
    ) throws UserIsPresentException {
        if (usersRepository.findByUsername(username).isPresent()) {
            throw new UserIsPresentException("User is present");
        }

        User user = User.builder()
            .username(username)
            .hashPassword(passwordEncoder.encode(password))
            .firstName(firstName)
            .lastName(lastName)
            .role(User.Role.USER)
            .workplace(User.Workplace.valueOf(workplace))
            .isNotLocked(true)
            .createdAt(LocalDateTime.now())
            .build();
        usersRepository.save(user);

        return ProfileDto.from(user);
    }
}
