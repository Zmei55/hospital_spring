package com.hospital_spring.laboratories.dto;

import com.hospital_spring.laboratories.model.Laboratory;
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
public class LaboratoryDto {
    @Schema(description = "Id of the laboratory", example = "1")
    private Long _id;
    @Schema(description = "Name of the laboratory", example = "LaboratoryName")
    private String name;
    @Schema(description = "Active is", example = "true")
    private boolean isActive;
    @Schema(description = "Id of the address of the laboratory", example = "1")
    private Long addressId;

    public static LaboratoryDto from(Laboratory laboratory) {
        return LaboratoryDto.builder()
            ._id(laboratory.getId())
            .name(laboratory.getName())
            .isActive(laboratory.isActive())
            .addressId(laboratory.getAddress() != null
                ? laboratory.getAddress().getId()
                : null)
            .build();
    }

    public static List<LaboratoryDto> from(List<Laboratory> laboratoryList) {
        return laboratoryList.stream()
            .map(LaboratoryDto::from)
            .collect(Collectors.toList());
    }
}
