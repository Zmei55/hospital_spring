package com.hospital_spring.services.services;

import com.hospital_spring.services.dto.FilterServiceDto;
import com.hospital_spring.services.dto.NewServiceDto;
import com.hospital_spring.services.dto.ServiceDto;
import com.hospital_spring.services.dto.UpdateServiceDto;

import java.util.List;

public interface ServicesService {
    ServiceDto addNew(NewServiceDto newService);

    ServiceDto getById(Long serviceId);

    List<ServiceDto> getAllActiveByFilter(FilterServiceDto filter);

    ServiceDto updateById(Long serviceId, UpdateServiceDto updateService);

    void deleteById(Long serviceId);
}
