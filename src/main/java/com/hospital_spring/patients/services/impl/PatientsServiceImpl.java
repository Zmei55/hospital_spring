package com.hospital_spring.patients.services.impl;

import com.hospital_spring.address.model.Address;
import com.hospital_spring.address.repositories.AddressesRepository;
import com.hospital_spring.patients.dto.NewPatientDto;
import com.hospital_spring.patients.dto.PatientAndAddressDto;
import com.hospital_spring.patients.dto.PatientFilterDto;
import com.hospital_spring.shared.exceptions.NotFoundException;
import com.hospital_spring.patients.dto.PatientDto;
import com.hospital_spring.patients.model.Patient;
import com.hospital_spring.patients.repositories.PatientsRepository;
import com.hospital_spring.patients.services.PatientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientsServiceImpl implements PatientsService {
    private final PatientsRepository patientsRepository;
    private final AddressesRepository addressesRepository;

    @Override
    public PatientDto addNew(NewPatientDto newPatient) {
        Address address = addressesRepository.findById(newPatient.getAddressId()).orElseThrow(
            () -> new NotFoundException("Address with id<" + newPatient.getAddressId() + "> not found")
        );

        Patient patient = Patient.builder()
            .name(newPatient.getName())
            .birthDate(newPatient.getBirthDate())
            .cardNumber(newPatient.getCardNumber())
            .gender(Patient.Gender.valueOf(newPatient.getGender()))
            .phoneNumber(newPatient.getPhoneNumber())
            .email(newPatient.getEmail())
            .identityDocument(newPatient.getIdentityDocument())
            .address(address)
            .createdAt(LocalDateTime.now())
            .build();

        patientsRepository.save(patient);

        return PatientDto.from(patient);
    }

    @Override
    public PatientAndAddressDto getById(Long patientId) {
        Patient patient = patientsRepository.findById(patientId)
            .orElseThrow(
                () -> new NotFoundException("Patient with id <" + patientId + "> not found")
            );

        return PatientAndAddressDto.from(patient);
    }

    @Override
    public List<PatientDto> getByFilter(PatientFilterDto filter) {
        String name = !filter.getName().isEmpty() ? filter.getName() : null;
        String birthDate = !filter.getBirthDate().isEmpty() ? filter.getBirthDate() : null;
        int number = filter.getCardNumber() != 0 ? filter.getCardNumber() : 0;

        List<Patient> patientList = patientsRepository.findAllByNameContainingIgnoreCaseOrBirthDateOrCardNumber(
            name,
            birthDate,
            number
        );

        return PatientDto.from(patientList);
    }

    @Override
    public PatientDto updateById(Long patientId, NewPatientDto newPatient) {
        Patient patient = patientsRepository.findById(patientId)
            .orElseThrow(
                () -> new NotFoundException("Patient with id <" + patientId + "> not found")
            );

        if (newPatient.getName() != null) patient.setName(newPatient.getName());
        if (!newPatient.getBirthDate().isEmpty()) patient.setBirthDate(newPatient.getBirthDate());
        if (newPatient.getCardNumber() != 0) patient.setCardNumber(newPatient.getCardNumber());
        if (newPatient.getGender() != null) patient.setGender(Patient.Gender.valueOf(newPatient.getGender()));
        if (newPatient.getPhoneNumber() != null) patient.setPhoneNumber(newPatient.getPhoneNumber());
        if (newPatient.getEmail() != null) patient.setEmail(newPatient.getEmail());
        if (newPatient.getIdentityDocument() != null) patient.setIdentityDocument(newPatient.getIdentityDocument());

        patientsRepository.save(patient);

        return PatientDto.from(patient);
    }

    @Override
    public void deleteById(Long patientId) {
        if (patientsRepository.existsById(patientId)) {
            patientsRepository.deleteById(patientId);
        } else {
            throw new NotFoundException("Patient with id <" + patientId + "> not found");
        }
    }
}
