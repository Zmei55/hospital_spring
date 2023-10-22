package com.hospital_spring.services.repositories;

import com.hospital_spring.services.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesRepository extends JpaRepository<Service, Long> {
    List<Service> findAllByNameContainingIgnoreCaseAndIsActiveIsTrue(String name);
}
