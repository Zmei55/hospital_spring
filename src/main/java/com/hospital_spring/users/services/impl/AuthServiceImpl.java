package com.hospital_spring.users.services.impl;

import com.hospital_spring.users.dto.NewUserDto;
import com.hospital_spring.users.dto.UserDto;
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
    public UserDto signUp(NewUserDto newUserDto) {
        // сделать проверку что пользователя с таким username в БД нет
        // if (usersRepository.findByUsername(newUserDto.getUsername()).isPresent()) {
        //     return ошибку о том что пользователь с таким username уже есть;
        // } // и регистрация не продолжится

        User user = User.builder()
            .username(newUserDto.getUsername())
            .hashPassword(passwordEncoder.encode(newUserDto.getPassword()))
            .firstName(newUserDto.getFirstName())
            .lastName(newUserDto.getLastName())
            .role(User.Role.USER)
            .workplace(newUserDto.getWorkplace())
            .isNotLocked(true)
            .createdDate(LocalDateTime.now())
            .build();
        usersRepository.save(user);

        return UserDto.from(user);
    }
}
