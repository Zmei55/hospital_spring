package com.hospital_spring.requests.controllers;

import com.hospital_spring.requests.controllers.api.RequestsApi;
import com.hospital_spring.requests.dto.FilterRequestDto;
import com.hospital_spring.requests.dto.NewRequestDto;
import com.hospital_spring.requests.dto.UpdateStatusRequestDto;
import com.hospital_spring.requests.services.RequestsService;
import com.hospital_spring.security.config.details.AuthenticatedUser;
import com.hospital_spring.shared.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RequestsController implements RequestsApi {
    private final RequestsService requestsService;

    @Override
    public ResponseEntity<ResponseDto> addNew(
        AuthenticatedUser currentUser,
        NewRequestDto newRequest
    ) {
        return ResponseEntity.status(201)
            .body(ResponseDto.fromCreated(
                requestsService.addNew(
                    currentUser,
                    newRequest
                )
            ));
    }

    @Override
    public ResponseEntity<ResponseDto> getById(Long requestId) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            requestsService.getById(requestId)
        ));
    }

    @Override
    public ResponseEntity<ResponseDto> getByFilter(FilterRequestDto filter) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            requestsService.getByFilter(filter)
        ));
    }

    @Override
    public ResponseEntity<ResponseDto> getRequestsDBCount() {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            requestsService.getRequestsDBCount()
        ));
    }

    @Override
    public ResponseEntity<ResponseDto> updateStatusById(Long requestId, UpdateStatusRequestDto updateStatus) {
        return ResponseEntity.ok(ResponseDto.fromSuccessful(
            requestsService.updateStatusById(requestId, updateStatus)
        ));
    }

    @Override
    public void deleteById(Long requestId) {
        requestsService.deleteById(requestId);
    }
}
