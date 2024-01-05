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
    @Schema(description = "Name of the user", example = "John")
    private String name;
    @Schema(description = "department of the user", example = "surgery")
    private String department;
    @Schema(description = "workplace of the user", example = "surgery")
    private String workplace;
    @Schema(description = "position of work")
    private String position;
    @Schema(description = "user is not locked", example = "true")
    private boolean isNotLocked;
}
