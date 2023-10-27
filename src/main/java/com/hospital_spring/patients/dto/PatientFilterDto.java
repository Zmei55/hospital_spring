package com.hospital_spring.patients.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PatientFilterDto {
    @Schema(description = "Name of the patient", example = "John Smith")
    private String name;
    @Schema(description = "Birth date of the patient", example = "24.05.1974")
    private String birthDate;
    @Schema(description = "Card number of the patient", example = "123456789")
    private int cardNumber;
}
