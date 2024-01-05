package com.hospital_spring.users.model;

import com.hospital_spring.requests.model.Request;
import com.hospital_spring.users.enums.Position;
import com.hospital_spring.users.enums.Role;
import com.hospital_spring.users.enums.Department;
import com.hospital_spring.users.enums.Workplace;
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
@Table(name = "users")
public class User {
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
    @Enumerated(value = EnumType.STRING)
    private Department department;
    @Enumerated(value = EnumType.STRING)
    private Workplace workplace;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Position position;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private boolean isNotLocked;
    @Column(columnDefinition = "varchar(400)")
    private String token;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Request.class)
    private List<Request> requests = new ArrayList<>();
}
