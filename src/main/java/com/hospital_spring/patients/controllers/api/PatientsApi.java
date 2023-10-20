package com.hospital_spring.patients.controllers.api;

import com.hospital_spring.patients.dto.PatientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                    schema = @Schema(implementation = PatientDto.class))})}
    )
    @PostMapping("/add")
    ResponseEntity<PatientDto> addNew(
        @RequestParam String firstName,
        @RequestParam String lastName,
        @RequestParam String birthDate,
        @RequestParam int cardNumber,
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
                    schema = @Schema(implementation = PatientDto.class))})}
    )
    @GetMapping("/{patient-id}")
    ResponseEntity<PatientDto> getById(@PathVariable("patient-id") Long patientId);

    @Operation(summary = "Get by filter", description = "Get patient by filter")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "patient by data",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PatientDto.class))})}
    )
    @PostMapping(value = "/")
    ResponseEntity<List<PatientDto>> getByFilter(
        @RequestParam String firstName,
        @RequestParam String lastName,
        @RequestParam String birthDate,
        @RequestParam int cardNumber
    );

    @Operation(summary = "Update", description = "Update patient data by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Update patient data by id",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PatientDto.class))})}
    )
    @PutMapping("/{patient-id}")
    ResponseEntity<PatientDto> updateById(
        @PathVariable("patient-id") Long patientId,
        @RequestParam String firstName,
        @RequestParam String lastName,
        @RequestParam String birthDate,
        @RequestParam int cardNumber,
        @RequestParam String gender,
        @RequestParam String phoneNumber,
        @RequestParam String email,
        @RequestParam String identityDocument
    );

    @Operation(summary = "Delete patient", description = "Delete patient by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delete patient by id",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PatientDto.class))})}
    )
    @DeleteMapping("/{patient-id}")
    void deleteById(@PathVariable("patient-id") Long patientId);
}
