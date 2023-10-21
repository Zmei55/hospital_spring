package com.hospital_spring.patients.controllers.api;

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
    @Tag(name = "Patients")
})
@RequestMapping("/api/patients")
public interface PatientsApi {
    @Operation(summary = "Add", description = "Add new patient")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "New patient",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @PostMapping("/add")
    ResponseEntity<ResponseDto> addNew(
        @RequestParam String firstName,
        @RequestParam String lastName,
        @RequestParam String birthDate,
        @RequestParam String cardNumber,
        @RequestParam String gender,
        @RequestParam String phoneNumber,
        @RequestParam String email,
        @RequestParam String identityDocument
    );

    @Operation(summary = "Get by id", description = "Get patient by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "patient by id",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @GetMapping("/{patient-id}")
    ResponseEntity<ResponseDto> getById(@PathVariable("patient-id") Long patientId);

    @Operation(summary = "Get by filter", description = "Get patient by filter")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "patient by data",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @PostMapping(value = "/")
    ResponseEntity<ResponseDto> getByFilter(
        @RequestParam String firstName,
        @RequestParam String lastName,
        @RequestParam String birthDate,
        @RequestParam String cardNumber
    );

    @Operation(summary = "Update", description = "Update patient data by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Update patient data by id",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})}
    )
    @PutMapping("/{patient-id}")
    ResponseEntity<ResponseDto> updateById(
        @PathVariable("patient-id") Long patientId,
        @RequestParam String firstName,
        @RequestParam String lastName,
        @RequestParam String birthDate,
        @RequestParam String cardNumber,
        @RequestParam String gender,
        @RequestParam String phoneNumber,
        @RequestParam String email,
        @RequestParam String identityDocument
    );

    @Operation(summary = "Delete patient", description = "Delete patient by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delete patient by id")}
    )
    @DeleteMapping("/{patient-id}")
    void deleteById(@PathVariable("patient-id") Long patientId);
}
