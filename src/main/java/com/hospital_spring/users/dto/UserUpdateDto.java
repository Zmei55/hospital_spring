package com.hospital_spring.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(description = "Update user")
public class UserUpdateDto {
    @Schema(description = "firstName of the user", example = "John")
    private String firstName;
    @Schema(description = "lastName of the user", example = "Smith")
    private String lastName;
}
