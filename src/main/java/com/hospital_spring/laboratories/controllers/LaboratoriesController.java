package com.hospital_spring.laboratories.controllers;

import com.hospital_spring.laboratories.controllers.api.LaboratoriesApi;
import com.hospital_spring.laboratories.dto.NewLaboratoryDto;
import com.hospital_spring.laboratories.dto.UpdateLaboratoryDto;
import com.hospital_spring.laboratories.services.LaboratoriesService;
import com.hospital_spring.shared.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LaboratoriesController implements LaboratoriesApi {
    private final LaboratoriesService laboratoriesService;

    @Override
    public ResponseEntity<ResponseDto> addNew(NewLaboratoryDto newLaboratory) {
        return ResponseEntity.status(201)
            .body(ResponseDto.fromCreated(
                laboratoriesService.addNew(newLaboratory)
            ));
    }

    @Override
    public ResponseEntity<ResponseDto> getById(Long laborId) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            laboratoriesService.getById(laborId)
        ));
    }

    @Override
    public ResponseEntity<ResponseDto> getAllActive() {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            laboratoriesService.getAllActive()
        ));
    }

    @Override
    public ResponseEntity<ResponseDto> updateById(Long laborId, UpdateLaboratoryDto updateLaboratory) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            laboratoriesService.updateById(laborId, updateLaboratory)
        ));
    }

    @Override
    public void deleteById(Long laborId) {
        laboratoriesService.deleteById(laborId);
    }
}
