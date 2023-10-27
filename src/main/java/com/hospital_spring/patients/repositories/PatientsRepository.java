package com.hospital_spring.patients.repositories;

import com.hospital_spring.patients.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientsRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAllByNameContainingIgnoreCaseOrBirthDateOrCardNumber(
        String name,
        String birthDate,
        int cardNumber
    );
}
