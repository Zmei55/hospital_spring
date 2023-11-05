package com.hospital_spring.laboratories.services;

import com.hospital_spring.laboratories.dto.LaboratoryDto;
import com.hospital_spring.laboratories.dto.NewLaboratoryDto;
import com.hospital_spring.laboratories.dto.UpdateLaboratoryDto;

import java.util.List;

public interface LaboratoriesService {
    LaboratoryDto addNew(NewLaboratoryDto newLaboratory);

    LaboratoryDto getById(Long laborId);

    List<LaboratoryDto> getAllActive();

    LaboratoryDto updateById(Long laborId, UpdateLaboratoryDto updateLaboratory);

    void deleteById(Long laborId);
}
