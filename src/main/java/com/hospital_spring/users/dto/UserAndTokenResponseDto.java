package com.hospital_spring.users.dto;

import com.hospital_spring.users.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserAndTokenResponseDto {
    private ProfileDto user;
    private String token;

    public static UserAndTokenResponseDto from(User user, String token) {
        return UserAndTokenResponseDto.builder()
            .user(ProfileDto.from(user))
            .token(token)
            .build();
    }
}
