package com.hospital_spring.users.services;

import com.hospital_spring.shared.exceptions.UserIsPresentException;
import com.hospital_spring.users.dto.ProfileDto;

public interface AuthService {
    ProfileDto signUp(
        String username,
        String password,
        String firstName,
        String lastName,
        String workplace
    ) throws UserIsPresentException;
}
