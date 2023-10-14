package com.hospital_spring.users.dto;

import com.hospital_spring.users.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "New user")
public class NewUserDto {
    @Schema(description = "username of the account", example = "johnsmith325")
    private String username;
    @Schema(description = "password of the account", example = "a9f3MH!")
    private String password;
    @Schema(description = "firstName of the user", example = "John")
    private String firstName;
    @Schema(description = "lastName of the user", example = "Smith")
    private String lastName;
    @Schema(description = "workplace of the user", example = "surgery")
    private User.Workplace workplace;
}
