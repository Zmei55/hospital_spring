package com.hospital_spring.users.dto;

import com.hospital_spring.users.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(description = "Registered user")
public class UserDto {
    @Schema(description = "id of the user", example = "1")
    private Long _id;
    @Schema(description = "username of the user", example = "username325")
    private String username;
    @Schema(description = "workplace of the user", example = "surgery")
    private String workplace;
    @Schema(description = "position of work")
    private String position;
    @Schema(description = "user is not locked", example = "true")
    private boolean isNotLocked;

    public static UserDto from(User user) {
        return UserDto.builder()
            ._id(user.getId())
            .username(user.getUsername())
            .workplace(user.getWorkplace().name())
            .position(user.getPosition().name())
            .isNotLocked(user.isNotLocked())
            .build();
    }
}
