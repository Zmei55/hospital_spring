package com.hospital_spring.requests.repositories;

import com.hospital_spring.requests.model.RequestDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestDetailsRepository extends JpaRepository<RequestDetails, Long> {
    List<RequestDetails> findAllByRequest_Id(Long requestId);
}
