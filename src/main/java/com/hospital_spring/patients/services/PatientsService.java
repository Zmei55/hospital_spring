package com.hospital_spring.patients.services;

import com.hospital_spring.patients.dto.FilterPatientDto;
import com.hospital_spring.patients.dto.NewPatientDto;
import com.hospital_spring.patients.dto.PatientDto;

import java.util.List;

public interface PatientsService {
    PatientDto addNew(NewPatientDto newPatientData);

    PatientDto getById(Long patientId);

    List<PatientDto> getByFilter(FilterPatientDto filter);

    PatientDto updateById(Long patientId, NewPatientDto newPatientData);

    void deleteById(Long patientId);
}
