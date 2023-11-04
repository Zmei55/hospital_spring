package com.hospital_spring.requests.services;

import com.hospital_spring.requests.dto.FilterRequestDto;
import com.hospital_spring.requests.dto.NewRequestDto;
import com.hospital_spring.requests.dto.RequestDto;
import com.hospital_spring.requests.dto.SearchRequestDto;
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

    RequestDto updateById(Long requestId, boolean isCompleted);

    void deleteById(Long requestId);
}
