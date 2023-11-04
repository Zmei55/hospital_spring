package com.hospital_spring.requests.model;

import com.hospital_spring.laboratories.model.Laboratory;
import com.hospital_spring.services.model.Service;
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
@Table(name = "requestDetails")
public class RequestDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = Request.class, fetch = FetchType.LAZY)
    private Request request;
    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = Service.class, fetch = FetchType.LAZY)
    private Service service;
    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = Laboratory.class, fetch = FetchType.LAZY)
    private Laboratory laboratory;
    @NotNull
    private boolean isCompleted;
    @NotNull
    private LocalDateTime createdAt;
}
