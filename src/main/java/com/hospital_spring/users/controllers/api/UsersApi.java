package com.hospital_spring.users.controllers.api;

import com.hospital_spring.users.dto.ProfileDto;
import com.hospital_spring.users.dto.UserDto;
import com.hospital_spring.users.dto.UserUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tags(value = {
    @Tag(name = "Users")
})
@RequestMapping("/api/users")
public interface UsersApi {
//    @Operation(summary = "Get current user info")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Current user",
//            content = {
//                @Content(mediaType = "application/json",
//                    schema = @Schema(implementation = ProfileDto.class))
//            }
//        )
//    })
//    @GetMapping("/profile")
//    ResponseEntity<ProfileDto> getProfile(
//        @Parameter(hidden = true)
//        @AuthenticationPrincipal AuthenticatedUser currentUser
//    );

    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User by id",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class))
            }
        )
    })
    @GetMapping("/{user-id}")
    ResponseEntity<UserDto> getUser(@PathVariable("user-id") Long userId);

//    @Operation(summary = "Update user")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Update user by id",
//            content = {
//                @Content(mediaType = "application/json",
//                    schema = @Schema(implementation = UserDto.class))
//            }
//        )
//    })
//    @PutMapping("/{userId}")
//    ResponseEntity<ProfileDto> updateUser(
//        @Parameter(hidden = true)
//        @AuthenticationPrincipal AuthenticatedUser currentUser,
//        @RequestBody UserUpdateDto updatedUser);
}
