package com.hospital_spring.patients.controllers;

import com.hospital_spring.patients.controllers.api.PatientsApi;
import com.hospital_spring.patients.dto.FilterPatientDto;
import com.hospital_spring.patients.dto.NewPatientDto;
import com.hospital_spring.patients.dto.PatientDto;
import com.hospital_spring.patients.services.PatientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PatientsController implements PatientsApi {
    private final PatientsService patientsService;

    @Override
    public ResponseEntity<PatientDto> addNew(NewPatientDto newPatient) {
        return ResponseEntity.status(201)
            .body(patientsService.addNew(newPatient));
    }

    @Override
    public ResponseEntity<PatientDto> getById(Long patientId) {
        return ResponseEntity.ok(patientsService.getById(patientId));
    }

    @Override
    public ResponseEntity<List<PatientDto>> getByFilter(FilterPatientDto filter) {
        return ResponseEntity.ok(patientsService.getByFilter(filter));
    }

    @Override
    public ResponseEntity<PatientDto> updateById(Long patientId, NewPatientDto newPatientData) {
        return ResponseEntity.ok(patientsService.updateById(patientId, newPatientData));
    }

    @Override
    public void deleteById(Long patientId) {
        patientsService.deleteById(patientId);
    }
}
