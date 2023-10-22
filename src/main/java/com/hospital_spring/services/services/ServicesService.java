package com.hospital_spring.services.services;

import com.hospital_spring.services.dto.ServiceDto;

import java.util.List;

public interface ServicesService {
    ServiceDto addNew(
        String name,
        String code
    );

    ServiceDto getById(Long serviceId);

    List<ServiceDto> getAllActiveByFilter(String filter);

    ServiceDto updateById(Long serviceId, String name, String code, boolean isActive);

    void deleteById(Long serviceId);
}
