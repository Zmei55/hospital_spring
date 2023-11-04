package com.hospital_spring.requests.repositories;

import com.hospital_spring.requests.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RequestsRepository extends JpaRepository<Request, Long> {
    @Query(value = "SELECT id FROM requests ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Long findTopByOrderByIdDesc();
}
