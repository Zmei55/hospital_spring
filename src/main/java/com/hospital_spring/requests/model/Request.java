package com.hospital_spring.requests.model;

import com.hospital_spring.patients.model.Patient;
import com.hospital_spring.users.model.User;
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
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long requestNumber;
    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = Patient.class)
    private Patient patient;
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = RequestDetails.class)
    private List<RequestDetails> requestDetails = new ArrayList<>();
    @NotNull
    private boolean isCompleted;
    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = User.class)
    private User user;
    @NotNull
    private LocalDateTime createdAt;
}
