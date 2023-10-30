package com.hospital_spring.users.model;

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
@Table(name = "users")
public class User {
    public enum Role {ADMIN, USER}

    public enum Workplace {SURGERY__TREATMENT_ROOM}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    private String hashPassword;
    private String email;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Workplace workplace;
    @NotNull
    private String position;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private boolean isNotLocked;
    @Column(columnDefinition = "varchar(400)")
    private String token;
}
