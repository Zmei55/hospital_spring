package com.hospital_spring.laboratories.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewLaboratoryDto {
    @Schema(description = "Name of the laboratory", example = "LaboratoryName")
    private String name;
    @Schema(description = "Id of the address of the laboratory", example = "1")
    private Long addressId;
}
