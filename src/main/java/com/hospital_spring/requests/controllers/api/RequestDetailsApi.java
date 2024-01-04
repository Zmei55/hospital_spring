package com.hospital_spring.requests.controllers.api;

import com.hospital_spring.requests.dto.UpdateStatusRequestDetailsDto;
import com.hospital_spring.shared.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tags(value = {
    @Tag(name = "RequestDetails")
})
@RequestMapping("/api/request-details")
public interface RequestDetailsApi {
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TREATMENT_ROOM')")
    @Operation(summary = "Get by id", description = "Get request details by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request details by id",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @GetMapping("/{request-details-id}")
    ResponseEntity<ResponseDto> getById(@PathVariable("request-details-id") Long detailsId);

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update", description = "Update request details status by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Update request details status by id",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @PutMapping("/{request-details-id}")
    ResponseEntity<ResponseDto> updateStatusById(
        @PathVariable("request-details-id") Long detailsId,
        @RequestBody UpdateStatusRequestDetailsDto updateStatus
        );

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete request details", description = "Delete request details by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delete request details by id")}
    )
    @DeleteMapping("/{request-details-id}")
    void deleteById(@PathVariable("request-details-id") Long detailsId);
}
