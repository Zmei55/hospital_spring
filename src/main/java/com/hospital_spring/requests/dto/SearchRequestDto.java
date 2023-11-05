package com.hospital_spring.requests.dto;

import com.hospital_spring.requests.model.SearchRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchRequestDto {
    @Schema(description = "Id of the request", example = "1")
    private Long _id;
    @Schema(description = "Name of the patient", example = "John Smith")
    private String patientName;
    @Schema(description = "Card number of the patient", example = "123456789")
    private int cardNumber;
    @Schema(description = "Id of the request", example = "1")
    private Long requestId;
    @Schema(description = "Number of the request", example = "1")
    private Long requestNumber;
    @Schema(description = "Date of creation")
    private LocalDateTime createdAt;
    @Schema(description = "Date of the request creation")
    private LocalDateTime requestCreatedAt;

    public static SearchRequestDto from(SearchRequest request) {
        return SearchRequestDto.builder()
            ._id(request.getId())
            .patientName(request.getPatientName())
            .cardNumber(request.getCardNumber())
            .requestId(request.getRequest().getId())
            .requestNumber(request.getRequestNumber())
            .requestCreatedAt(request.getRequestCreatedAt())
            .createdAt(request.getCreatedAt())
            .build();
    }

    public static List<SearchRequestDto> from(List<SearchRequest> requestList) {
        return requestList.stream()
            .map(SearchRequestDto::from)
            .collect(Collectors.toList());
    }
}
