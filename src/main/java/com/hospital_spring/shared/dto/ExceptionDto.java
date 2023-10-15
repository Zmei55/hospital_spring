package com.hospital_spring.shared.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(description = "Exception details")
public class ExceptionDto {
    @Schema(description = "Exception text")
    private String message;
    @Schema(description = "Exception http-status")
    private int status;
    @Schema(description = "Exception date")
    private LocalDateTime timestamp;

    public ExceptionDto(int status, String message) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
