package com.hospital_spring.requests.services;

import com.hospital_spring.requests.dto.NewRequestDetailsDto;
import com.hospital_spring.requests.dto.RequestDetailsDto;
import com.hospital_spring.requests.dto.UpdateStatusRequestDetailsDto;
import com.hospital_spring.requests.model.Request;
import com.hospital_spring.requests.model.RequestDetails;

import java.util.List;

public interface RequestDetailsService {
    RequestDetails addNew(Long serviceId, Long laborId, Request request);

    List<RequestDetails> addNewList(List<NewRequestDetailsDto> newDetailsList, Request request);

    RequestDetailsDto getById(Long detailsId);

    RequestDetailsDto updateStatusById(
        Long detailsId,
        UpdateStatusRequestDetailsDto updateStatus
    );

    void deleteById(Long detailsId);
}
