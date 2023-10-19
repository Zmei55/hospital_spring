package com.hospital_spring.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
@Schema(name = "ResponseDto", description = "Request details")
public class ResponseDto {
    @Schema(description = "Message text")
    private String message;
    @Schema(description = "HTTP-status")
    private int status;
    @Schema(description = "Data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    @Schema(description = "Date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime timestamp;

    public static ResponseDto getResponseException(Exception exception, HttpStatus status) {
        return ResponseDto.builder()
            .message(exception.getMessage())
            .status(status.value())
            .timestamp(LocalDateTime.now())
            .build();
    }
}
