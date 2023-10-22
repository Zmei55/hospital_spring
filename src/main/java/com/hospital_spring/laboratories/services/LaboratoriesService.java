package com.hospital_spring.laboratories.services;

import com.hospital_spring.laboratories.dto.LaboratoryDto;

import java.util.List;

public interface LaboratoriesService {
    LaboratoryDto addNew(String name);

    LaboratoryDto getById(Long laborId);

    List<LaboratoryDto> getAllActive();

    LaboratoryDto updateById(Long laborId, String name, boolean isActive);

    void deleteById(Long laborId);
}
