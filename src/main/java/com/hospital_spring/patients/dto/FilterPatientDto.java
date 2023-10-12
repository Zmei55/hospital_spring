package com.hospital_spring.patients.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilterPatientDto {
    @Schema(description = "First name of the patient", example = "John")
    private String firstName;
    @Schema(description = "Last name of the patient", example = "Smith")
    private String lastName;
    @Schema(description = "Birth date of the patient", example = "24.05.1974")
    private LocalDateTime birthDate;
    @Schema(description = "Card number of the patient", example = "123456789")
    private int cardNumber;
}