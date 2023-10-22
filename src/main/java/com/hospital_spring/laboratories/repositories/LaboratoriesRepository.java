package com.hospital_spring.laboratories.repositories;

import com.hospital_spring.laboratories.model.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaboratoriesRepository extends JpaRepository<Laboratory, Long> {
    List<Laboratory> findAllByIsActiveIsTrue();
}
