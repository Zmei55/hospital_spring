package com.hospital_spring.laboratories.services.impl;

import com.hospital_spring.address.model.Address;
import com.hospital_spring.address.repositories.AddressesRepository;
import com.hospital_spring.laboratories.dto.LaboratoryDto;
import com.hospital_spring.laboratories.dto.NewLaboratoryDto;
import com.hospital_spring.laboratories.dto.UpdateLaboratoryDto;
import com.hospital_spring.laboratories.model.Laboratory;
import com.hospital_spring.laboratories.repositories.LaboratoriesRepository;
import com.hospital_spring.laboratories.services.LaboratoriesService;
import com.hospital_spring.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LaboratoriesServiceImpl implements LaboratoriesService {
    private final LaboratoriesRepository laboratoriesRepository;
    private final AddressesRepository addressesRepository;

    @Override
    public LaboratoryDto addNew(NewLaboratoryDto newLaboratory) {
        Address address = addressesRepository.findById(newLaboratory.getAddressId()).orElseThrow(
            () -> new NotFoundException("Address with id <" + newLaboratory.getAddressId() + "> not found")
        );

        Laboratory laboratory = Laboratory.builder()
            .name(newLaboratory.getName())
            .isActive(true)
            .address(address)
            .createdAt(LocalDateTime.now())
            .build();

        laboratoriesRepository.save(laboratory);

        return LaboratoryDto.from(laboratory);
    }

    @Override
    public LaboratoryDto getById(Long laborId) {
        Laboratory laboratory = laboratoriesRepository.findById(laborId)
            .orElseThrow(
                () -> new NotFoundException("Laboratory with id <" + laborId + "> not found")
            );

        return LaboratoryDto.from(laboratory);
    }

    @Override
    public List<LaboratoryDto> getAllActive() {
        List<Laboratory> laboratoryList = laboratoriesRepository.findAllByIsActiveIsTrue();

        return LaboratoryDto.from(laboratoryList);
    }

    @Override
    public LaboratoryDto updateById(Long laborId, UpdateLaboratoryDto updateLaboratory) {
        Laboratory laboratory = laboratoriesRepository.findById(laborId)
            .orElseThrow(
                () -> new NotFoundException("Laboratory with id <" + laborId + "> not found")
            );

        laboratory.setName(updateLaboratory.getName());
        laboratory.setActive(updateLaboratory.isActive());

        laboratoriesRepository.save(laboratory);

        return LaboratoryDto.from(laboratory);
    }

    @Override
    public void deleteById(Long laborId) {
        if (laboratoriesRepository.existsById(laborId)) {
            laboratoriesRepository.deleteById(laborId);
        } else {
            throw new NotFoundException("Laboratory with id <" + laborId + "> not found");
        }
    }
}
