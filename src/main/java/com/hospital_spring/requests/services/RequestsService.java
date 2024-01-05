package com.hospital_spring.requests.services;

import com.hospital_spring.requests.dto.*;
import com.hospital_spring.security.config.details.AuthenticatedUser;

import java.util.List;

public interface RequestsService {
    RequestDto addNew(
        AuthenticatedUser currentUser,
        NewRequestDto newRequest
    );

    RequestDto getById(Long requestId);

    List<SearchRequestDto> getByFilter(FilterRequestDto filter);

    Long getRequestsDBCount();

    RequestDto updateStatusById(
        Long requestId,
        UpdateStatusRequestDto updateStatus
    );

    void deleteById(Long requestId);
}
