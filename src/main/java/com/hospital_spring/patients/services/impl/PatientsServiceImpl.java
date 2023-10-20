package com.hospital_spring.patients.services.impl;

import com.hospital_spring.shared.exceptions.NotFoundException;
import com.hospital_spring.patients.dto.FilterPatientDto;
import com.hospital_spring.patients.dto.NewPatientDto;
import com.hospital_spring.patients.dto.PatientDto;
import com.hospital_spring.patients.model.Patient;
import com.hospital_spring.patients.repositories.PatientsRepository;
import com.hospital_spring.patients.services.PatientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientsServiceImpl implements PatientsService {
    private final PatientsRepository patientsRepository;

    @Override
    public PatientDto addNew(NewPatientDto newPatientData) {
        Patient patient = Patient.builder()
            .firstName(newPatientData.getFirstName())
            .lastName(newPatientData.getLastName())
            .birthDate(LocalDateTime.parse(newPatientData.getBirthDate(), DateTimeFormatter.ISO_DATE_TIME))
            .cardNumber(newPatientData.getCardNumber())
            .gender(Patient.Gender.valueOf(newPatientData.getGender()))
            .phoneNumber(newPatientData.getPhoneNumber())
            .email(newPatientData.getEmail())
            .identityDocument(newPatientData.getIdentityDocument())
            .createdAt(LocalDateTime.now())
            .build();

        patientsRepository.save(patient);

        return PatientDto.from(patient);
    }

    @Override
    public PatientDto getById(Long patientId) {
        Patient patient = patientsRepository.findById(patientId)
            .orElseThrow(
                ()->new NotFoundException("Patient with id <" + patientId + "> not found")
            );

        return PatientDto.from(patient);
    }

    @Override
    public List<PatientDto> getByFilter(FilterPatientDto filter) {
        List<Patient> patientList = patientsRepository.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrBirthDateOrCardNumber(
            filter.getFirstName(),
            filter.getLastName(),
            filter.getBirthDate(),
            filter.getCardNumber()
        );

        return PatientDto.from(patientList);
    }

    @Override
    public PatientDto updateById(Long patientId, NewPatientDto newPatientData) {
        Patient patient = patientsRepository.findById(patientId)
            .orElseThrow(
                () -> new NotFoundException("Patient with id <" + patientId + "> not found")
            );

        patient.setFirstName(newPatientData.getFirstName());
        patient.setLastName(newPatientData.getLastName());
        patient.setBirthDate(LocalDateTime.parse(newPatientData.getBirthDate(), DateTimeFormatter.ISO_DATE_TIME));
        patient.setCardNumber(newPatientData.getCardNumber());
        patient.setGender(Patient.Gender.valueOf(newPatientData.getGender()));
        patient.setPhoneNumber(newPatientData.getPhoneNumber());
        patient.setEmail(newPatientData.getEmail());
        patient.setIdentityDocument(newPatientData.getIdentityDocument());

        patientsRepository.save(patient);

        return PatientDto.from(patient);
    }

    @Override
    public void deleteById(Long patientId) {
        if(patientsRepository.existsById(patientId)) {
            patientsRepository.deleteById(patientId);
        } else {
            throw new NotFoundException("Patient with id <" + patientId + "> not found");
        }
    }
}
