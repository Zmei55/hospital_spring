package com.hospital_spring.users.controllers.api;

import com.hospital_spring.users.dto.NewUserDto;
import com.hospital_spring.users.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tags(value = {
    @Tag(name = "Authentication")
})
@RequestMapping("/api/auth")
public interface AuthApi {
    @Operation(summary = "SignUn")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User is registered",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class))
            }
        )
    })
    @PostMapping("/register")
    ResponseEntity<UserDto> signUp(@RequestBody NewUserDto newUserDto);
}

// при logout отправлять пустой tokens (null)
