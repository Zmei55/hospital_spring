package com.hospital_spring.users.services.impl;

import com.hospital_spring.users.dto.NewUserDto;
import com.hospital_spring.users.dto.UserDto;
import com.hospital_spring.users.model.User;
import com.hospital_spring.users.repositories.UsersRepository;
import com.hospital_spring.users.services.AuthService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto signUp(NewUserDto newUserDto) {
        User user = User.builder()
            .username(newUserDto.getUsername())
            .hashPassword(passwordEncoder.encode(newUserDto.getPassword()))
            .role(User.Role.USER)
            .firstName(newUserDto.getFirstName())
            .lastName(newUserDto.getLastName())
            .createdDate(LocalDateTime.now())
            .build();
        usersRepository.save(user);

        return UserDto.from(user);
    }
}