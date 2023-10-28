package com.hospital_spring.requests.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateStatusRequestDetailsDto {
    @Schema(description = "Completed is", example = "true")
    private boolean isCompleted;
}
