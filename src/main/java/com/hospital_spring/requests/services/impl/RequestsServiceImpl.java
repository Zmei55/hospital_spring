package com.hospital_spring.requests.services.impl;

import com.hospital_spring.patients.model.Patient;
import com.hospital_spring.patients.repositories.PatientsRepository;
import com.hospital_spring.requests.dto.*;
import com.hospital_spring.requests.model.Request;
import com.hospital_spring.requests.model.RequestDetails;
import com.hospital_spring.requests.repositories.RequestsRepository;
import com.hospital_spring.requests.services.RequestDetailsService;
import com.hospital_spring.requests.services.RequestsService;
import com.hospital_spring.security.config.details.AuthenticatedUser;
import com.hospital_spring.shared.exceptions.NotFoundException;
import com.hospital_spring.users.model.User;
import com.hospital_spring.users.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class RequestsServiceImpl implements RequestsService {
    private final RequestsRepository requestsRepository;
    private final UsersRepository usersRepository;
    private final PatientsRepository patientsRepository;
    private final RequestDetailsService detailsService;

    @Override
    public RequestDto addNew(
        AuthenticatedUser currentUser,
        NewRequestDto newRequest
    ) {
        User user = usersRepository.findById(currentUser.getUser().getId()).orElseThrow(
            () -> new NotFoundException("User with id <" + currentUser.getUser().getId() + "> not found")
        );

        Patient patient = patientsRepository.findById(newRequest.getPatientId()).orElseThrow(
            () -> new NotFoundException("Patient with id <" + newRequest.getPatientId() + "> not found")
        );

        Request request = Request.builder()
            .requestNumber(newRequest.getRequestNumber())
            .patient(patient)
            .isCompleted(false)
            .owner(user)
            .createdAt(LocalDateTime.now())
            .build();

        requestsRepository.save(request);

        List<RequestDetails> detailsList = detailsService.addNewList(newRequest.getRequestDetails(), request);

        requestsRepository.save(request);

        return RequestDto.from(request, detailsList);
    }

    @Override
    public RequestDto getById(Long requestId) {
        Request request = requestsRepository.findById(requestId).orElseThrow(
            () -> new NotFoundException("Request with id <" + requestId + "> not found")
        );

        return RequestDto.from(request);
    }

    @Override
    public List<RequestDto> getByFilter(FilterRequestDto filter) {
        String name = !filter.getPatientName().isEmpty() ? filter.getPatientName() : null;
        int cardNumber = filter.getPatientCardNumber() != 0 ? filter.getPatientCardNumber() : 0;
        Long requestNumber = filter.getRequestNumber() != 0 ? filter.getRequestNumber() : 0;
        String createdDate = !filter.getRequestCreatedAt().isEmpty() ? filter.getRequestCreatedAt() : null;

        List<Request> requestList = requestsRepository.findByNameContainingIgnoreCaseAndCardNumberAndRequestNumberAndCreatedAt(
            name,
            cardNumber,
            requestNumber,
            createdDate
        );

        return RequestDto.from(requestList);
    }

    @Override
    public Long getRequestsDBCount() {
        return requestsRepository.count();
    }

    @Override
    public RequestDto updateById(Long requestId, boolean isCompleted) {
        Request request = requestsRepository.findById(requestId).orElseThrow(
            () -> new NotFoundException("Request with id <" + requestId + "> not found")
        );

        request.setCompleted(isCompleted);
        requestsRepository.save(request);

        return RequestDto.from(request);
    }

    @Override
    public void deleteById(Long requestId) {
        if (requestsRepository.existsById(requestId)) {
            requestsRepository.deleteById(requestId);
        } else {
            throw new NotFoundException("Request with id <" + requestId + "> not found");
        }
    }
}
