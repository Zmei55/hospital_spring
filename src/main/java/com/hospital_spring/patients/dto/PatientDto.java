package com.hospital_spring.patients.dto;

import com.hospital_spring.patients.model.Patient;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PatientDto {
    @Schema(description = "Id of the patient", example = "1")
    private Long _id;
    @Schema(description = "Name of the patient", example = "John Smith")
    private String name;
    @Schema(description = "Birth date of the patient", example = "24.05.1974")
    private String birthDate;
    @Schema(description = "Card number of the patient", example = "123456789")
    private int cardNumber;
    @Schema(description = "Gender of the patient", example = "männlich")
    private String gender;
    @Schema(description = "Phone number of the patient", example = "+4917789743197")
    private String phoneNumber;
    @Schema(description = "Email of the patient", example = "johnsmith325@mail.com")
    private String email;
    @Schema(description = "Identity document of the patient", example = "Pass")
    private String identityDocument;
    @Schema(description = "Id of the address of the patient", example = "1")
    private Long addressId;

    public static PatientDto from(Patient patient) {
        return PatientDto.builder()
            ._id(patient.getId())
            .name(patient.getName())
            .birthDate(patient.getBirthDate())
            .cardNumber(patient.getCardNumber())
            .gender(patient.getGender().name())
            .phoneNumber(patient.getPhoneNumber())
            .email(patient.getEmail())
            .identityDocument(patient.getIdentityDocument())
            .addressId(patient.getAddress() != null
                ? patient.getAddress().getId()
                : null)
            .build();
    }

    public static List<PatientDto> from(List<Patient> patientList) {
        return patientList.stream()
            .map(PatientDto::from)
            .collect(Collectors.toList());
    }
}
