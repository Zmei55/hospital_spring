package com.hospital_spring.laboratories.model;

import com.hospital_spring.address.model.Address;
import com.hospital_spring.requests.model.RequestDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "laboratories")
public class Laboratory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private boolean isActive;
    @NotNull
    private LocalDateTime createdAt;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Address.class)
    private Address address;
    @OneToMany(mappedBy = "laboratory", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = RequestDetails.class)
    private List<RequestDetails> requestDetails = new ArrayList<>();
}
