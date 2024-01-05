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
@Schema(description = "New user registration")
public class NewUserDto {
    @Schema(description = "username of the user", example = "username")
    private String username;
    @Schema(description = "password of the user", example = "password")
    private String password;
    @Schema(description = "Name of the user", example = "John")
    private String name;
    @Schema(description = "department of the user", example = "surgery")
    private String department;
    @Schema(description = "workplace of the user", example = "surgery")
    private String workplace;
    @Schema(description = "position of work")
    private String position;
}
