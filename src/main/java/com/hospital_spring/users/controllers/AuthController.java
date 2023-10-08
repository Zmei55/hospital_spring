package com.hospital_spring.users.controllers;

import com.hospital_spring.users.controllers.api.AuthApi;
import com.hospital_spring.users.dto.NewUserDto;
import com.hospital_spring.users.dto.UserDto;
import com.hospital_spring.users.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//@CrossOrigin("http://localhost:3000/")
public class AuthController implements AuthApi {
    private final AuthService authService;

    @Override
    public ResponseEntity<UserDto> signUp(NewUserDto newUserDto) {
        return ResponseEntity.status(201)
            .body(authService.signUp(newUserDto));
    }
}
