package com.hospital_spring.users.services.impl;

import com.hospital_spring.security.config.details.AuthenticatedUser;
import com.hospital_spring.shared.exceptions.NotFoundException;
import com.hospital_spring.users.dto.ProfileDto;
import com.hospital_spring.users.dto.UserUpdateDto;
import com.hospital_spring.users.enums.Department;
import com.hospital_spring.users.enums.Position;
import com.hospital_spring.users.enums.Workplace;
import com.hospital_spring.users.model.User;
import com.hospital_spring.users.repositories.UsersRepository;
import com.hospital_spring.users.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        if (updatedUser.getName() != null) user.setName(updatedUser.getName());
        if (updatedUser.getDepartment() != null) user.setDepartment(Department.valueOf(updatedUser.getDepartment()));
        if (updatedUser.getWorkplace() != null) user.setWorkplace(Workplace.valueOf(updatedUser.getWorkplace()));
        if (updatedUser.getPosition() != null) user.setPosition(Position.valueOf(updatedUser.getPosition()));
        if (updatedUser.isNotLocked() != user.isNotLocked()) user.setNotLocked(updatedUser.isNotLocked());

        usersRepository.save(user);

        return ProfileDto.from(user);
    }

    @Override
    public void deleteById(Long userId) {
        if (usersRepository.existsById(userId)) {
            usersRepository.deleteById(userId);
        } else {
            throw new NotFoundException("User with id <" + userId + "> not found");
        }
    }
}
