package com.hospital_spring.patients.model;

import com.hospital_spring.address.model.Address;
import com.hospital_spring.requests.model.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

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
    private String name;
    @NotNull
    private String birthDate;
    @NotNull
    private int cardNumber;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private String phoneNumber;
    private String email;
    private String identityDocument;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.REFRESH, targetEntity = Request.class, fetch = FetchType.LAZY)
    private List<Request> requests;
    @NotNull
    private LocalDateTime createdAt;
    @OneToOne(cascade = CascadeType.ALL, targetEntity = Address.class, fetch = FetchType.LAZY)
    private Address address;
}
