package com.hospital_spring.services.controllers;

import com.hospital_spring.services.controllers.api.ServicesApi;
import com.hospital_spring.services.dto.FilterServiceDto;
import com.hospital_spring.services.dto.NewServiceDto;
import com.hospital_spring.services.dto.UpdateServiceDto;
import com.hospital_spring.services.services.ServicesService;
import com.hospital_spring.shared.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ServicesController implements ServicesApi {
    private final ServicesService servicesService;

    @Override
    public ResponseEntity<ResponseDto> addNew(NewServiceDto newService) {
        return ResponseEntity.status(201)
            .body(ResponseDto.fromCreated(
                servicesService.addNew(newService)
            ));
    }

    @Override
    public ResponseEntity<ResponseDto> getById(Long serviceId) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            servicesService.getById(serviceId)
        ));
    }

    @Override
    public ResponseEntity<ResponseDto> getAllActiveByFilter(FilterServiceDto filter) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            servicesService.getAllActiveByFilter(filter)
        ));
    }

    @Override
    public ResponseEntity<ResponseDto> updateById(Long serviceId, UpdateServiceDto updateService) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            servicesService.updateById(serviceId, updateService)
        ));
    }

    @Override
    public void deleteById(Long serviceId) {
        servicesService.deleteById(serviceId);
    }
}
