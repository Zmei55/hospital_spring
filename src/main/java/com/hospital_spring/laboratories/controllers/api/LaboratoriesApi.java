package com.hospital_spring.laboratories.controllers.api;

import com.hospital_spring.laboratories.dto.NewLaboratoryDto;
import com.hospital_spring.laboratories.dto.UpdateLaboratoryDto;
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
    @Tag(name = "Laboratories")
})
@RequestMapping("/api/labors")
public interface LaboratoriesApi {
    @Operation(summary = "Add", description = "Add new laboratory")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "New laboratory",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @PostMapping("/add")
    ResponseEntity<ResponseDto> addNew(@RequestBody NewLaboratoryDto newLaboratory);

    @Operation(summary = "Get by id", description = "Get laboratory by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "laboratory by id",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @GetMapping("/{labor-id}")
    ResponseEntity<ResponseDto> getById(@PathVariable("labor-id") Long laborId);

    @Operation(summary = "Get all", description = "Get all is active")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "laboratories is active",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @GetMapping("/")
    ResponseEntity<ResponseDto> getAllActive();

    @Operation(summary = "Update", description = "Update laboratory data by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Update laboratory data by id",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @PutMapping("/{labor-id}")
    ResponseEntity<ResponseDto> updateById(
        @PathVariable("labor-id") Long laborId,
        @RequestBody UpdateLaboratoryDto updateLaboratory
        );

    @Operation(summary = "Delete laboratory", description = "Delete laboratory by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delete laboratory by id")}
    )
    @DeleteMapping("/{labor-id}")
    void deleteById(@PathVariable("labor-id") Long laborId);
}
