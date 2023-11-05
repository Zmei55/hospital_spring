package com.hospital_spring.services.dto;

import com.hospital_spring.services.model.Service;
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
public class ServiceDto {
    @Schema(description = "Id of the service", example = "1")
    private Long _id;
    @Schema(description = "Name of the service", example = "Immunglobulin A (IgA)")
    private String name;
    @Schema(description = "Code of the service", example = "L18.36.00.0.001")
    private String code;
    @Schema(description = "Active is", example = "true")
    private boolean isActive;

    public static ServiceDto from(Service service) {
        return ServiceDto.builder()
            ._id(service.getId())
            .name(service.getName())
            .code(service.getCode())
            .isActive(service.isActive())
            .build();
    }

    public static List<ServiceDto> from(List<Service> serviceList) {
        return serviceList.stream()
            .map(ServiceDto::from)
            .collect(Collectors.toList());
    }
}
