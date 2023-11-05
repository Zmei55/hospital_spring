package com.hospital_spring.requests.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilterRequestDto {
    @Schema(description = "Name of the patient", example = "John Smith")
    private String patientName;
    @Schema(description = "Card number of the patient", example = "123456789")
    private int cardNumber;
    @Schema(description = "Number of the request", example = "1")
    private Long requestNumber;
    @Schema(description = "Date of creation")
    private String requestCreatedAt;
}
