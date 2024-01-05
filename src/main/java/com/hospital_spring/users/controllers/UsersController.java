package com.hospital_spring.users.controllers;

import com.hospital_spring.security.config.details.AuthenticatedUser;
import com.hospital_spring.shared.dto.ResponseDto;
import com.hospital_spring.users.controllers.api.UsersApi;
import com.hospital_spring.users.dto.UserUpdateDto;
import com.hospital_spring.users.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersController implements UsersApi {
    private final UsersService usersService;

    @Override
    public ResponseEntity<ResponseDto> getProfile(AuthenticatedUser currentUser) {
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(ResponseDto.fromSuccessful(usersService.getProfile(currentUser)));
    }

    @Override
    public ResponseEntity<ResponseDto> getUserById(Long userId) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(usersService.getUserById(userId)));
    }

    @Override
    public ResponseEntity<ResponseDto> updateUser(Long userId, UserUpdateDto updatedUser) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(usersService.updateUser(userId, updatedUser)));
    }

    @Override
    public void deleteById(Long userId) {
        usersService.deleteById(userId);
    }
}
