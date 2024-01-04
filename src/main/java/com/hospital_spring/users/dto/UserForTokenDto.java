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
public class UserForTokenDto {
    private Long id;
    private String username;
    private String workplace;
    private boolean isNotLocked;

    public static UserForTokenDto from(User user) {
        return UserForTokenDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .workplace(user.getWorkplace().name())
            .isNotLocked(user.isNotLocked())
            .build();
    }
}
