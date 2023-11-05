package com.hospital_spring.patients.controllers;

import com.hospital_spring.patients.controllers.api.PatientsApi;
import com.hospital_spring.patients.dto.NewPatientDto;
import com.hospital_spring.patients.dto.PatientFilterDto;
import com.hospital_spring.patients.services.PatientsService;
import com.hospital_spring.shared.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatientsController implements PatientsApi {
    private final PatientsService patientsService;

    @Override
    public ResponseEntity<ResponseDto> addNew(NewPatientDto newPatient) {
        return ResponseEntity.status(201)
            .body(ResponseDto.fromCreated(
                patientsService.addNew(newPatient)));
    }

    @Override
    public ResponseEntity<ResponseDto> getById(Long patientId) {
        return ResponseEntity.ok(
            ResponseDto.fromSuccessful(
                patientsService.getById(patientId)));
    }

    @Override
    public ResponseEntity<ResponseDto> getByFilter(PatientFilterDto filter) {
        return ResponseEntity.ok(
            ResponseDto.fromSuccessful(
                patientsService.getByFilter(filter)));
    }

    @Override
    public ResponseEntity<ResponseDto> updateById(Long patientId, NewPatientDto newPatient) {
        return ResponseEntity.ok(
            ResponseDto.fromSuccessful(
                patientsService.updateById(patientId, newPatient)));
    }

    @Override
    public void deleteById(Long patientId) {
        patientsService.deleteById(patientId);
    }
}
