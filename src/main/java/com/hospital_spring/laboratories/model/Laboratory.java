package com.hospital_spring.laboratories.model;

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
    @OneToOne(cascade = CascadeType.ALL, targetEntity = Address.class)
    private Address address;
}
