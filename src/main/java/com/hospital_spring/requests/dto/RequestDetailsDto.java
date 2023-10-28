package com.hospital_spring.requests.dto;

import com.hospital_spring.requests.model.RequestDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestDetailsDto {
    @Schema(description = "Id of the details", example = "1")
    private Long _id;
    @Schema(description = "Id of the service", example = "1")
    private Long serviceId;
    @Schema(description = "Id of the laboratory", example = "1")
    private Long laborId;
    @Schema(description = "Completed is", example = "true")
    private boolean isCompleted;
    @Schema(description = "Date of creation")
    private String createdAt;

    public static RequestDetailsDto from(RequestDetails details) {
        return RequestDetailsDto.builder()
            ._id(details.getId())
            .serviceId(details.getService().getId())
            .laborId(details.getLabor().getId())
            .isCompleted(details.isCompleted())
            .createdAt(details.getCreatedAt().toString())
            .build();
    }

    public static List<RequestDetailsDto> from(List<RequestDetails> detailsList) {
        return detailsList.stream()
            .map(RequestDetailsDto::from)
            .collect(Collectors.toList());
    }
}
