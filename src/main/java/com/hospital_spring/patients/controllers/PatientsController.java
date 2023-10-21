package com.hospital_spring.patients.controllers;

import com.hospital_spring.patients.controllers.api.PatientsApi;
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
    public ResponseEntity<ResponseDto> addNew(
        String firstName,
        String lastName,
        String birthDate,
        String cardNumber,
        String gender,
        String phoneNumber,
        String email,
        String identityDocument
    ) {
        return ResponseEntity.status(201)
            .body(ResponseDto.fromCreated(
                patientsService.addNew(
                    firstName,
                    lastName,
                    birthDate,
                    cardNumber,
                    gender,
                    phoneNumber,
                    email,
                    identityDocument
                )));
    }

    @Override
    public ResponseEntity<ResponseDto> getById(Long patientId) {
        return ResponseEntity.ok(
            ResponseDto.fromSuccessful(
                patientsService.getById(patientId)));
    }

    @Override
    public ResponseEntity<ResponseDto> getByFilter(
        String firstName,
        String lastName,
        String birthDate,
        String cardNumber
    ) {
        return ResponseEntity.ok(
            ResponseDto.fromSuccessful(
                patientsService.getByFilter(
                    firstName,
                    lastName,
                    birthDate,
                    cardNumber
                )));
    }

    @Override
    public ResponseEntity<ResponseDto> updateById(
        Long patientId,
        String firstName,
        String lastName,
        String birthDate,
        String cardNumber,
        String gender,
        String phoneNumber,
        String email,
        String identityDocument
    ) {
        return ResponseEntity.ok(
            ResponseDto.fromSuccessful(
                patientsService.updateById(
                    patientId,
                    firstName,
                    lastName,
                    birthDate,
                    cardNumber,
                    gender,
                    phoneNumber,
                    email,
                    identityDocument
                )));
    }

    @Override
    public void deleteById(Long patientId) {
        patientsService.deleteById(patientId);
    }
}
