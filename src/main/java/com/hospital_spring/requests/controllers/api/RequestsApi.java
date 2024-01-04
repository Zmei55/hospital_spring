package com.hospital_spring.requests.controllers.api;

import com.hospital_spring.requests.dto.FilterRequestDto;
import com.hospital_spring.requests.dto.NewRequestDto;
import com.hospital_spring.requests.dto.UpdateStatusRequestDto;
import com.hospital_spring.security.config.details.AuthenticatedUser;
import com.hospital_spring.shared.dto.ResponseDto;
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
    @Tag(name = "Requests")
})
@RequestMapping("/api/requests")
public interface RequestsApi {
    @Operation(summary = "Add", description = "Add new request")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "New request",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @PostMapping("/add")
    ResponseEntity<ResponseDto> addNew(
        @Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser currentUser,
        @RequestBody NewRequestDto newRequest
        );

    @Operation(summary = "Get by id", description = "Get request by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request by id",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @GetMapping("/{request-id}")
    ResponseEntity<ResponseDto> getById(@PathVariable("request-id") Long requestId);

    @Operation(summary = "Get by filter", description = "Get request by filter")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request by filter",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @PostMapping("/")
    ResponseEntity<ResponseDto> getByFilter(@RequestBody FilterRequestDto filter);

    @Operation(summary = "Get count", description = "Get request count")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request count",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @PostMapping("/count")
    ResponseEntity<ResponseDto> getRequestsDBCount();

    @Operation(summary = "Update", description = "Update request status by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Update request status by id",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @PutMapping("/{request-id}")
    ResponseEntity<ResponseDto> updateStatusById(
        @PathVariable("request-id") Long requestId,
        @RequestParam UpdateStatusRequestDto updateStatus
    );

    @Operation(summary = "Delete request", description = "Delete request by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delete request by id")}
    )
    @DeleteMapping("/{request-id}")
    void deleteById(@PathVariable("request-id") Long requestId);
}
