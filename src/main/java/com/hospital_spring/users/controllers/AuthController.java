package com.hospital_spring.users.controllers;

import com.hospital_spring.shared.dto.ResponseDto;
import com.hospital_spring.users.controllers.api.AuthApi;
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
    public ResponseEntity<ResponseDto> signUp(
        String username,
        String password,
        String name,
        String workplace,
        String position
    ) {
        return ResponseEntity.status(HttpStatus.CREATED.value())
            .body(ResponseDto.fromCreated(
                authService.signUp(
                    username,
                    password,
                    name,
                    workplace,
                    position
                )));
    }
}
