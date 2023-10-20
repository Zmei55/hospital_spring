package com.hospital_spring.patients.services.impl;

import com.hospital_spring.shared.exceptions.NotFoundException;
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
    public PatientDto addNew(
        String firstName,
        String lastName,
        String birthDate,
        int cardNumber,
        String gender,
        String phoneNumber,
        String email,
        String identityDocument
    ) {
        Patient patient = Patient.builder()
            .firstName(firstName)
            .lastName(lastName)
            .birthDate(LocalDateTime.parse(birthDate, DateTimeFormatter.ISO_DATE_TIME))
            .cardNumber(cardNumber)
            .gender(Patient.Gender.valueOf(gender))
            .phoneNumber(phoneNumber)
            .email(email)
            .identityDocument(identityDocument)
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
    public List<PatientDto> getByFilter(
        String firstName,
        String lastName,
        String birthDate,
        int cardNumber
    ) {
        List<Patient> patientList = patientsRepository.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrBirthDateOrCardNumber(
            firstName,
            lastName,
            LocalDateTime.parse(birthDate, DateTimeFormatter.ISO_DATE_TIME),
            cardNumber
        );

        return PatientDto.from(patientList);
    }

    @Override
    public PatientDto updateById(
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
        Patient patient = patientsRepository.findById(patientId)
            .orElseThrow(
                () -> new NotFoundException("Patient with id <" + patientId + "> not found")
            );

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setBirthDate(LocalDateTime.parse(birthDate, DateTimeFormatter.ISO_DATE_TIME));
        patient.setCardNumber(cardNumber);
        patient.setGender(Patient.Gender.valueOf(gender));
        patient.setPhoneNumber(phoneNumber);
        patient.setEmail(email);
        patient.setIdentityDocument(identityDocument);

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
