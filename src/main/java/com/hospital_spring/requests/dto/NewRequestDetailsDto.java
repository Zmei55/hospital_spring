package com.hospital_spring.requests.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewRequestDetailsDto {
    @Schema(description = "Id of the service", example = "1")
    private Long serviceId;
    @Schema(description = "Id of the laboratory", example = "1")
    private Long laborId;
}
