package com.hospital_spring.laboratories.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateLaboratoryDto {
    @Schema(description = "Name of the laboratory", example = "LaboratoryName")
    private String name;
    @Schema(description = "Active is", example = "true")
    private boolean isActive;
}
