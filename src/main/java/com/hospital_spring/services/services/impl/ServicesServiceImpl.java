package com.hospital_spring.services.services.impl;

import com.hospital_spring.services.dto.ServiceDto;
import com.hospital_spring.services.model.Service;
import com.hospital_spring.services.repositories.ServicesRepository;
import com.hospital_spring.services.services.ServicesService;
import com.hospital_spring.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServicesServiceImpl implements ServicesService {
    private final ServicesRepository servicesRepository;

    @Override
    public ServiceDto addNew(String name, String code) {
        Service service = Service.builder()
            .name(name)
            .code(code)
            .isActive(true)
            .createdAt(LocalDateTime.now())
            .build();

        servicesRepository.save(service);

        return ServiceDto.from(service);
    }

    @Override
    public ServiceDto getById(Long serviceId) {
        Service service = servicesRepository.findById(serviceId).orElseThrow(
            () -> new NotFoundException("Service with id <" + serviceId + "> not found")
        );

        return ServiceDto.from(service);
    }

    @Override
    public List<ServiceDto> getAllActiveByFilter(String filter) {
        List<Service> serviceList = servicesRepository.findAllByNameContainingIgnoreCaseAndIsActiveIsTrue(filter);

        return ServiceDto.from(serviceList);
    }

    @Override
    public ServiceDto updateById(Long serviceId, String name, String code, boolean isActive) {
        Service service = servicesRepository.findById(serviceId).orElseThrow(
            () -> new NotFoundException("Service with id <" + serviceId + "> not found")
        );

        service.setName(name);
        service.setCode(code);
        service.setActive(isActive);
        servicesRepository.save(service);

        return ServiceDto.from(service);
    }

    @Override
    public void deleteById(Long serviceId) {
        if (servicesRepository.existsById(serviceId)) {
            servicesRepository.deleteById(serviceId);
        } else {
            throw new NotFoundException("Service with id <" + serviceId + "> not found");
        }
    }
}
