package com.hospital_spring.patients.services;

import com.hospital_spring.patients.dto.PatientDto;

import java.util.List;

public interface PatientsService {
    PatientDto addNew(
        String firstName,
        String lastName,
        String birthDate,
        int cardNumber,
        String gender,
        String phoneNumber,
        String email,
        String identityDocument
    );

    PatientDto getById(Long patientId);

    List<PatientDto> getByFilter(
        String firstName,
        String lastName,
        String birthDate,
        int cardNumber
    );

    PatientDto updateById(
        Long patientId,
        String firstName,
        String lastName,
        String birthDate,
        int cardNumber,
        String gender,
        String phoneNumber,
        String email,
        String identityDocument
    );

    void deleteById(Long patientId);
}
