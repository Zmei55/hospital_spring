package com.hospital_spring.users.controllers.api;

import com.hospital_spring.shared.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                    schema = @Schema(implementation = ResponseDto.class))
            }
        )
    })
    @PostMapping("/register")
    ResponseEntity<ResponseDto> signUp(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String name,
        @RequestParam String department,
        @RequestParam String workplace,
        @RequestParam String position
    );
}
