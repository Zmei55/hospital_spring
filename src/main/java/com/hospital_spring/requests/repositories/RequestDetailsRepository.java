package com.hospital_spring.requests.repositories;

import com.hospital_spring.requests.model.RequestDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestDetailsRepository extends JpaRepository<RequestDetails, Long> {
}
