package com.hospital_spring.requests.repositories;

import com.hospital_spring.requests.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestsRepository extends JpaRepository<Request, Long> {
    @Query("SELECT r FROM Request r INNER JOIN r.patient p WHERE p.name = :name AND p.cardNumber = :cardNumber AND r.requestNumber = :requestNumber AND r.createdAt = :dateCreation")
    List<Request> findByNameContainingIgnoreCaseAndCardNumberAndRequestNumberAndCreatedAt(
        @Param("name") String name,
        @Param("cardNumber") int cardNumber,
        @Param("requestNumber") Long requestNumber,
        @Param("dateCreation") String createdAt
    );
}
