package com.hospital_spring.users.controllers;

import com.hospital_spring.shared.dto.ResponseDto;
import com.hospital_spring.users.controllers.api.AuthApi;
import com.hospital_spring.users.dto.NewUserDto;
import com.hospital_spring.users.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;

    @Override
    public ResponseEntity<ResponseDto> signUp(NewUserDto newUser) {
        return ResponseEntity.status(HttpStatus.CREATED.value())
            .body(ResponseDto.fromCreated(authService.signUp(newUser)));
    }
}
