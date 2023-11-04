package com.hospital_spring.requests.services.impl;

import com.hospital_spring.patients.model.Patient;
import com.hospital_spring.patients.repositories.PatientsRepository;
import com.hospital_spring.requests.dto.*;
import com.hospital_spring.requests.model.Request;
import com.hospital_spring.requests.model.RequestDetails;
import com.hospital_spring.requests.model.SearchRequest;
import com.hospital_spring.requests.repositories.RequestDetailsRepository;
import com.hospital_spring.requests.repositories.RequestsRepository;
import com.hospital_spring.requests.repositories.SearchRequestRepository;
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
    private final RequestDetailsRepository detailsRepository;
    private final SearchRequestRepository searchRequestRepository;
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
            .user(user)
            .createdAt(LocalDateTime.now())
            .build();

        requestsRepository.save(request);

        SearchRequest searchRequest = SearchRequest.builder()
            .patientName(patient.getName())
            .cardNumber(patient.getCardNumber())
            .request(request)
            .requestNumber(request.getRequestNumber())
            .createdAt(LocalDateTime.now())
            .build();

        searchRequestRepository.save(searchRequest);

        List<RequestDetails> detailsList = detailsService.addNewList(newRequest.getRequestDetails(), request);

        return RequestDto.from(request, detailsList);
    }

    @Override
    public RequestDto getById(Long requestId) {
        Request request = requestsRepository.findById(requestId).orElseThrow(
            () -> new NotFoundException("Request with id <" + requestId + "> not found")
        );
        List<RequestDetails> detailsList = detailsRepository.findAllByRequest_Id(requestId);

        return RequestDto.from(request, detailsList);
    }

    @Override
    public List<SearchRequestDto> getByFilter(FilterRequestDto filter) {
        String patientName = filter.getPatientName() == null || filter.getPatientName().isEmpty() ? null : filter.getPatientName();

        List<SearchRequest> requestList = searchRequestRepository.findAllByPatientNameContainingIgnoreCaseOrCardNumberOrRequestNumberOrCreatedAt(
            patientName,
            filter.getCardNumber(),
            filter.getRequestNumber(),
            filter.getRequestCreatedAt()
        );

        if (!requestList.isEmpty()) {
            return SearchRequestDto.from(requestList);
        } else {
            throw new NotFoundException("Requests not found");
        }
    }

    @Override
    public Long getRequestsDBCount() {
        return requestsRepository.findTopByOrderByIdDesc();
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
