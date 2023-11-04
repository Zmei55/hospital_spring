package com.hospital_spring.requests.repositories;

import com.hospital_spring.requests.model.SearchRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchRequestRepository extends JpaRepository<SearchRequest, Long> {
//    @Query("SELECT r FROM Request r INNER JOIN r.patient p WHERE p.name = :name OR p.cardNumber = :cardNumber OR r.requestNumber = :requestNumber OR r.createdAt = :dateCreation")
//    @Query("SELECT r FROM Request r INNER JOIN r.patient p WHERE p.name = :name AND p.cardNumber = :cardNumber AND r.requestNumber = :requestNumber AND r.createdAt = :dateCreation")
    List<SearchRequest> findAllByPatientNameContainingIgnoreCaseOrCardNumberOrRequestNumberOrCreatedAt(
        String patientName, int cardNumber, Long requestNumber, String createdAt
    );
}
