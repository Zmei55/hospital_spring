package com.hospital_spring.services.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewServiceDto {
    @Schema(description = "Name of the service", example = "Immunglobulin A (IgA)")
    private String name;
    @Schema(description = "Code of the service", example = "L18.36.00.0.001")
    private String code;
}
