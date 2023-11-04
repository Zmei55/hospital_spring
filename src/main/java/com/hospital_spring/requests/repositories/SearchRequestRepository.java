package com.hospital_spring.requests.repositories;

import com.hospital_spring.requests.model.SearchRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SearchRequestRepository extends JpaRepository<SearchRequest, Long> {
    List<SearchRequest> findAllByPatientNameContainingIgnoreCaseOrCardNumberOrRequestNumberOrCreatedAt(
        String patientName, int cardNumber, Long requestNumber, String createdAt
    );
}
