package com.hospital_spring.users.services;

import com.hospital_spring.users.dto.NewUserDto;
import com.hospital_spring.users.dto.ProfileDto;

public interface AuthService {
    ProfileDto signUp(NewUserDto newUser);
}
