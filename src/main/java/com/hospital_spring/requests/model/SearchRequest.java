package com.hospital_spring.requests.model;

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
public class SearchRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String patientName;
    @NotNull
    private int cardNumber;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, targetEntity = Request.class, fetch = FetchType.LAZY)
    private Request request;
    @NotNull
    private Long requestNumber;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime requestCreatedAt;
}
