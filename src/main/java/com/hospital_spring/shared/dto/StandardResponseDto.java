package com.hospital_spring.shared.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(name = "StandardResponseDto", description = "Request details")
public class StandardResponseDto {
    @Schema(description = "Message text")
    private String message;
    @Schema(description = "HTTP-status")
    private int status;
    @Schema(description = "Data")
    private Object data;
}
