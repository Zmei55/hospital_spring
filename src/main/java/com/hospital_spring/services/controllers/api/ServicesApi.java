package com.hospital_spring.services.controllers.api;

import com.hospital_spring.services.dto.FilterServiceDto;
import com.hospital_spring.services.dto.NewServiceDto;
import com.hospital_spring.services.dto.UpdateServiceDto;
import com.hospital_spring.shared.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tags(value = {
    @Tag(name = "Services")
})
@RequestMapping("/api/services")
public interface ServicesApi {
    @Operation(summary = "Add", description = "Add new service")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "New service",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @PostMapping("/add")
    ResponseEntity<ResponseDto> addNew(@RequestBody NewServiceDto newService);

    @Operation(summary = "Get by id", description = "Get service by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "service by id",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @GetMapping("/{service-id}")
    ResponseEntity<ResponseDto> getById(@PathVariable("service-id") Long serviceId);

    @Operation(summary = "Get all", description = "Get all services is active")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "services is active",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @PostMapping("/")
    ResponseEntity<ResponseDto> getAllActiveByFilter(@RequestBody FilterServiceDto filter);

    @Operation(summary = "Update", description = "Update service data by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Update service data by id",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @PutMapping("/{service-id}")
    ResponseEntity<ResponseDto> updateById(
        @PathVariable("service-id") Long serviceId,
        @RequestBody UpdateServiceDto updateService
        );

    @Operation(summary = "Delete service", description = "Delete service by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delete service by id")}
    )
    @DeleteMapping("/{service-id}")
    void deleteById(@PathVariable("service-id") Long serviceId);
}
