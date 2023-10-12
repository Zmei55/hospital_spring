package com.hospital_spring.shared.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(description = "Exception details")
public class ExceptionDto {
    @Schema(description = "Exception text")
    private String message;
    @Schema(description = "Exception http-status")
    private int status;
}
