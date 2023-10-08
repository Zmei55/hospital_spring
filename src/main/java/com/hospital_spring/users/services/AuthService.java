package com.hospital_spring.users.services;

import com.hospital_spring.users.dto.NewUserDto;
import com.hospital_spring.users.dto.UserDto;

public interface AuthService {
    UserDto signUp(NewUserDto newUserDto);
}
