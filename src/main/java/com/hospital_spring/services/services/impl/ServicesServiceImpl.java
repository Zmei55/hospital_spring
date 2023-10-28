package com.hospital_spring.services.services.impl;

import com.hospital_spring.services.dto.FilterServiceDto;
import com.hospital_spring.services.dto.NewServiceDto;
import com.hospital_spring.services.dto.ServiceDto;
import com.hospital_spring.services.dto.UpdateServiceDto;
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
    public ServiceDto addNew(NewServiceDto newService) {
        Service service = Service.builder()
            .name(newService.getName())
            .code(newService.getCode())
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
    public List<ServiceDto> getAllActiveByFilter(FilterServiceDto filter) {
        List<Service> serviceList = servicesRepository.findAllByNameContainingIgnoreCaseAndIsActiveTrue(filter.getName());

        return ServiceDto.from(serviceList);
    }

    @Override
    public ServiceDto updateById(Long serviceId, UpdateServiceDto updateService) {
        Service service = servicesRepository.findById(serviceId).orElseThrow(
            () -> new NotFoundException("Service with id <" + serviceId + "> not found")
        );

        if (updateService.getName() != null) service.setName(updateService.getName());
        if (updateService.getCode() != null) service.setCode(updateService.getCode());
        service.setActive(updateService.isActive());
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
