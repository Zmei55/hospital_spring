package com.hospital_spring.users.services;

import com.hospital_spring.shared.dto.ResponseDto;
import com.hospital_spring.shared.exceptions.UserIsPresentException;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ResponseDto> signUp(
        String username,
        String password,
        String firstName,
        String lastName,
        String workplace
    ) throws UserIsPresentException;
}
