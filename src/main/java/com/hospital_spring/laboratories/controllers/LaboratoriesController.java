package com.hospital_spring.laboratories.controllers;

import com.hospital_spring.laboratories.controllers.api.LaboratoriesApi;
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
    public ResponseEntity<ResponseDto> addNew(String name) {
        return ResponseEntity.status(201)
            .body(ResponseDto.fromCreated(
                laboratoriesService.addNew(name)
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
    public ResponseEntity<ResponseDto> updateById(Long patientId, String name, boolean isActive) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            laboratoriesService.updateById(patientId, name, isActive)
        ));
    }

    @Override
    public void deleteById(Long laborId) {
        laboratoriesService.deleteById(laborId);
    }
}
