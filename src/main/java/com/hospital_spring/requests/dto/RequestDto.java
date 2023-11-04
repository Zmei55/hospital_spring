package com.hospital_spring.requests.dto;

import com.hospital_spring.requests.model.Request;
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
public class RequestDto {
    @Schema(description = "Id of the request", example = "1")
    private Long _id;
    @Schema(description = "Number of the request", example = "1")
    private Long requestNumber;
    @Schema(description = "Id of the patient", example = "1")
    private Long patientId;
    @Schema(description = "Id's of the details", example = "1")
    private List<RequestDetailsDto> requestDetails;
    @Schema(description = "Completed is", example = "true")
    private boolean isCompleted;
    @Schema(description = "Id of the user", example = "1")
    private Long owner;
    @Schema(description = "Date of creation")
    private String createdAt;

    public static RequestDto from(Request request) {
        return RequestDto.builder()
            ._id(request.getId())
            .requestNumber(request.getRequestNumber())
            .patientId(request.getPatient().getId())
            .isCompleted(request.isCompleted())
            .owner(request.getUser().getId())
            .createdAt(request.getCreatedAt().toString())
            .build();
    }

    public static RequestDto from(Request request, List<RequestDetails> detailsList) {
        return RequestDto.builder()
            ._id(request.getId())
            .requestNumber(request.getRequestNumber())
            .patientId(request.getPatient().getId())
            .requestDetails(RequestDetailsDto.from(detailsList))
            .isCompleted(request.isCompleted())
            .owner(request.getUser().getId())
            .createdAt(request.getCreatedAt().toString())
            .build();
    }

    public static List<RequestDto> from(List<Request> requestList) {
        return requestList.stream()
            .map(RequestDto::from)
            .collect(Collectors.toList());
    }
}
