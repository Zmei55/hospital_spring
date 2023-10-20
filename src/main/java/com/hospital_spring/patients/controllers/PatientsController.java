package com.hospital_spring.patients.controllers;

import com.hospital_spring.patients.controllers.api.PatientsApi;
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
    public ResponseEntity<PatientDto> addNew(
        String firstName,
        String lastName,
        String birthDate,
        int cardNumber,
        String gender,
        String phoneNumber,
        String email,
        String identityDocument
    ) {
        return ResponseEntity.status(201)
            .body(patientsService.addNew(
                firstName,
                lastName,
                birthDate,
                cardNumber,
                gender,
                phoneNumber,
                email,
                identityDocument
            ));
    }

    @Override
    public ResponseEntity<PatientDto> getById(Long patientId) {
        return ResponseEntity.ok(patientsService.getById(patientId));
    }

    @Override
    public ResponseEntity<List<PatientDto>> getByFilter(
        String firstName,
        String lastName,
        String birthDate,
        int cardNumber
    ) {
        return ResponseEntity.ok(patientsService.getByFilter(
            firstName,
            lastName,
            birthDate,
            cardNumber
        ));
    }

    @Override
    public ResponseEntity<PatientDto> updateById(
        Long patientId,
        String firstName,
        String lastName,
        String birthDate,
        int cardNumber,
        String gender,
        String phoneNumber,
        String email,
        String identityDocument
    ) {
        return ResponseEntity.ok(patientsService.updateById(
            patientId,
            firstName,
            lastName,
            birthDate,
            cardNumber,
            gender,
            phoneNumber,
            email,
            identityDocument
            ));
    }

    @Override
    public void deleteById(Long patientId) {
        patientsService.deleteById(patientId);
    }
}
