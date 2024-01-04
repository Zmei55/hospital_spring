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
@Schema(description = "Profile user")
public class ProfileDto {
    @Schema(description = "id of the account", example = "1")
    private Long _id;
    @Schema(description = "Name of the user", example = "John Smith")
    private String name;
    @Schema(description = "username of the account", example = "johnsmith325")
    private String username;
    @Schema(description = "role of the user", example = "USER")
    private String role;
    @Schema(description = "workplace of the user", example = "surgery")
    private String workplace;
    @Schema(description = "position of work")
    private String position;
    @Schema(description = "user is not locked", example = "true")
    private boolean isNotLocked;
    @Schema(description = "token of the account", example = "ksjdnf.sdkjnfsk.sdkfkdlfk")
    private String token;

    public static ProfileDto from(User user) {
        return ProfileDto.builder()
            ._id(user.getId())
            .name(user.getName())
            .username(user.getUsername())
            .role(user.getRole().name())
            .workplace(user.getWorkplace().name())
            .position(user.getPosition().name())
            .isNotLocked(user.isNotLocked())
            .token(user.getToken())
            .build();
    }
}
