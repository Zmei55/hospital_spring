package com.hospital_spring.users.services;

import com.hospital_spring.users.dto.ProfileDto;
import com.hospital_spring.users.dto.UserDto;
import com.hospital_spring.users.dto.UserUpdateDto;

public interface UsersService {
//    ProfileDto getProfile(AuthenticatedUser currentUser);

    UserDto getUser(Long userId);

//    ProfileDto updateUser(AuthenticatedUser currentUser, UserUpdateDto updatedUser);
}
