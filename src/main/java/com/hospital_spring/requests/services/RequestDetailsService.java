package com.hospital_spring.requests.services;

import com.hospital_spring.requests.dto.NewRequestDetailsDto;
import com.hospital_spring.requests.dto.RequestDetailsDto;
import com.hospital_spring.requests.dto.UpdateStatusRequestDetailsDto;
import com.hospital_spring.requests.model.RequestDetails;

public interface RequestDetailsService {
    RequestDetails addNew(NewRequestDetailsDto newDetails);

    RequestDetailsDto getById(Long detailsId);

    RequestDetailsDto updateStatusById(
        Long detailsId,
        UpdateStatusRequestDetailsDto updateStatus
    );

    void deleteById(Long detailsId);
}
