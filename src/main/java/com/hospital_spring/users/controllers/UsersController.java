package com.hospital_spring.users.controllers;

import com.hospital_spring.security.config.details.AuthenticatedUser;
import com.hospital_spring.users.controllers.api.UsersApi;
import com.hospital_spring.users.dto.ProfileDto;
import com.hospital_spring.users.dto.UserDto;
import com.hospital_spring.users.dto.UserUpdateDto;
import com.hospital_spring.users.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersController implements UsersApi {
    private final UsersService usersService;

    @Override
    public ResponseEntity<ProfileDto> getProfile(AuthenticatedUser currentUser) {
        ProfileDto profile = usersService.getProfile(currentUser);

        return ResponseEntity.ok(profile);
    }

    @Override
    public ResponseEntity<UserDto> getUser(Long userId) {
        return ResponseEntity.ok(usersService.getUser(userId));
    }

    @Override
    public ResponseEntity<ProfileDto> updateUser(Long userId, UserUpdateDto updatedUser) {
        return ResponseEntity.ok(usersService.updateUser(userId, updatedUser));
    }
}
