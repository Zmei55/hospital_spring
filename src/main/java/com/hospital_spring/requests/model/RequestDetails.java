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
    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = Request.class)
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    private Request request;
    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = Service.class)
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Service service;
    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = Laboratory.class)
    @JoinColumn(name = "labor_id", referencedColumnName = "id")
    private Laboratory labor;
    @NotNull
    private boolean isCompleted;
    @NotNull
    private LocalDateTime createdAt;
}
