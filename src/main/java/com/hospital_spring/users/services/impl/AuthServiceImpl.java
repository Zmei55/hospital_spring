package com.hospital_spring.users.services.impl;

import com.hospital_spring.shared.exceptions.UserIsPresentException;
import com.hospital_spring.users.dto.ProfileDto;
import com.hospital_spring.users.enums.Department;
import com.hospital_spring.users.enums.Position;
import com.hospital_spring.users.enums.Role;
import com.hospital_spring.users.enums.Workplace;
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
        String name,
        String department,
        String workplace,
        String position
    ) throws UserIsPresentException {
        if (usersRepository.findByUsername(username).isPresent()) {
            throw new UserIsPresentException("User is present");
        }

        User user = User.builder()
            .username(username)
            .hashPassword(passwordEncoder.encode(password))
            .name(name)
            .role(Role.USER)
            .department(Department.valueOf(department))
            .workplace(Workplace.valueOf(workplace))
            .position(Position.valueOf(position))
            .isNotLocked(true)
            .createdAt(LocalDateTime.now())
            .build();
        usersRepository.save(user);

        return ProfileDto.from(user);
    }
}
