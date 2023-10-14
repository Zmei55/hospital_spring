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
    private Long id;
    @Schema(description = "firstName of the user", example = "John")
    private String firstName;
    @Schema(description = "lastName of the user", example = "Smith")
    private String lastName;
    @Schema(description = "username of the account", example = "johnsmith325")
    private String username;
    @Schema(description = "role of the user", example = "USER")
    private String role;
    @Schema(description = "workplace of the user", example = "surgery")
    private String workplace;
    @Schema(description = "user is not locked", example = "true")
    private boolean isNotLocked;

    public static ProfileDto from(User user) {
        return ProfileDto.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .username(user.getUsername())
            .role(user.getRole().name())
            .workplace(user.getWorkplace().name())
            .isNotLocked(user.isNotLocked())
            .build();
    }
}
