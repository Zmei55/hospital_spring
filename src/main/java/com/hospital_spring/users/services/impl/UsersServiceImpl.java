package com.hospital_spring.users.services.impl;

import com.hospital_spring.security.config.details.AuthenticatedUser;
import com.hospital_spring.shared.exceptions.NotFoundException;
import com.hospital_spring.users.dto.ProfileDto;
import com.hospital_spring.users.dto.UserUpdateDto;
import com.hospital_spring.users.model.User;
import com.hospital_spring.users.repositories.UsersRepository;
import com.hospital_spring.users.services.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    @Override
    public ProfileDto getProfile(AuthenticatedUser currentUser) {
        Long userId = currentUser.getUser().getId();

        User user = usersRepository.findById(userId)
            .orElseThrow(IllegalArgumentException::new);

        return ProfileDto.from(user);
    }

    @Override
    public ProfileDto getUserById(Long userId) {
        User user = usersRepository.findById(userId)
            .orElseThrow(
                () -> new NotFoundException("User with id <" + userId + "> not found")
            );

        return ProfileDto.from(user);
    }

    @Override
    public ProfileDto updateUser(Long userId, UserUpdateDto updatedUser) {
        User user = usersRepository.findById(userId)
            .orElseThrow(
                () -> new NotFoundException("User with id <" + userId + "not found")
            );

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setWorkplace(User.Workplace.valueOf(updatedUser.getWorkplace()));
        user.setNotLocked(updatedUser.isNotLocked());

        usersRepository.save(user);

        return ProfileDto.from(user);
    }
}
