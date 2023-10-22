package com.hospital_spring.services.controllers;

import com.hospital_spring.services.controllers.api.ServicesApi;
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
    public ResponseEntity<ResponseDto> addNew(String name, String code) {
        return ResponseEntity.status(201)
            .body(ResponseDto.fromCreated(
                servicesService.addNew(name, code)
            ));
    }

    @Override
    public ResponseEntity<ResponseDto> getById(Long serviceId) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            servicesService.getById(serviceId)
        ));
    }

    @Override
    public ResponseEntity<ResponseDto> getAllActiveByFilter(String filter) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            servicesService.getAllActiveByFilter(filter)
        ));
    }

    @Override
    public ResponseEntity<ResponseDto> updateById(Long serviceId, String name, String code, boolean isActive) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            servicesService.updateById(serviceId, name, code, isActive)
        ));
    }

    @Override
    public void deleteById(Long serviceId) {
        servicesService.deleteById(serviceId);
    }
}
