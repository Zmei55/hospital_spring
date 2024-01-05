package com.hospital_spring.users.services;

import com.hospital_spring.users.dto.ProfileDto;

public interface AuthService {
    ProfileDto signUp(
        String username,
        String password,
        String name,
        String department,
        String workplace,
        String position
    );
}
