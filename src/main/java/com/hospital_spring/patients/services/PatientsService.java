package com.hospital_spring.patients.services;

import com.hospital_spring.patients.dto.NewPatientDto;
import com.hospital_spring.patients.dto.PatientAndAddressDto;
import com.hospital_spring.patients.dto.PatientDto;
import com.hospital_spring.patients.dto.PatientFilterDto;

import java.util.List;

public interface PatientsService {
    PatientDto addNew(NewPatientDto newPatient);

    PatientAndAddressDto getById(Long patientId);

    List<PatientDto> getByFilter(PatientFilterDto filter);

    PatientDto updateById(Long patientId, NewPatientDto newPatient);

    void deleteById(Long patientId);
}
