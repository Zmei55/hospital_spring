package com.hospital_spring.requests.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewRequestDto {
    @Schema(description = "Number of the request", example = "1")
    private Long requestNumber;
    @Schema(description = "Id of the patient", example = "1")
    private Long patientId;
    @Schema(description = "Id's of the details", example = "1")
    private List<NewRequestDetailsDto> requestDetails = new ArrayList<>();
}
