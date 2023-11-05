package com.hospital_spring.shared.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Exception details")
public class ExceptionDto {
    @Schema(description = "Exception http-status")
    private int status;
    @Schema(description = "Exception text")
    private String message;
    @Schema(description = "Exception date")
    private LocalDateTime timestamp;
}
