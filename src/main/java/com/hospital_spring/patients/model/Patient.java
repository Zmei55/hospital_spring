package com.hospital_spring.patients.model;

import com.hospital_spring.address.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "patients")
public class Patient {
    public enum Gender {MALE, FEMALE, DIVERSE}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String birthDate;
    @NotNull
    private int cardNumber;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private String phoneNumber;
    private String email;
    private String identityDocument;
    @NotNull
    private LocalDateTime createdAt;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Address.class)
    private Address address;
}
