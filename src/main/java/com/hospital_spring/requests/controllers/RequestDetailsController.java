package com.hospital_spring.requests.controllers;

import com.hospital_spring.requests.controllers.api.RequestDetailsApi;
import com.hospital_spring.requests.dto.NewRequestDetailsDto;
import com.hospital_spring.requests.dto.UpdateStatusRequestDetailsDto;
import com.hospital_spring.requests.services.RequestDetailsService;
import com.hospital_spring.shared.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RequestDetailsController implements RequestDetailsApi {
    private final RequestDetailsService detailsService;

    @Override
    public ResponseEntity<ResponseDto> addNew(NewRequestDetailsDto newDetails) {
        return ResponseEntity.status(201)
            .body(ResponseDto.fromCreated(
                detailsService.addNew(newDetails)
            ));
    }

    @Override
    public ResponseEntity<ResponseDto> getById(Long detailsId) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            detailsService.getById(detailsId)
        ));
    }

    @Override
    public ResponseEntity<ResponseDto> updateStatusById(Long detailsId, UpdateStatusRequestDetailsDto updateStatus) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            detailsService.updateStatusById(detailsId, updateStatus)
        ));
    }

    @Override
    public void deleteById(Long detailsId) {
        detailsService.deleteById(detailsId);
    }
}
