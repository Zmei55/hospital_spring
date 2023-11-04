package com.hospital_spring.requests.services.impl;

import com.hospital_spring.laboratories.model.Laboratory;
import com.hospital_spring.laboratories.repositories.LaboratoriesRepository;
import com.hospital_spring.requests.dto.NewRequestDetailsDto;
import com.hospital_spring.requests.dto.RequestDetailsDto;
import com.hospital_spring.requests.dto.UpdateStatusRequestDetailsDto;
import com.hospital_spring.requests.model.Request;
import com.hospital_spring.requests.model.RequestDetails;
import com.hospital_spring.requests.repositories.RequestDetailsRepository;
import com.hospital_spring.requests.services.RequestDetailsService;
import com.hospital_spring.services.model.Service;
import com.hospital_spring.services.repositories.ServicesRepository;
import com.hospital_spring.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class RequestDetailsServiceImpl implements RequestDetailsService {
    private final RequestDetailsRepository detailsRepository;
    private final ServicesRepository servicesRepository;
    private final LaboratoriesRepository laborsRepository;

    @Override
    public RequestDetails addNew(Long serviceId, Long laborId, Request request) {
        Service service = servicesRepository.findById(serviceId).orElseThrow(
            () -> new NotFoundException("Service with id <" + serviceId + "> not found")
        );
        Laboratory laboratory = laborsRepository.findById(laborId).orElseThrow(
            () -> new NotFoundException("Laboratory with id <" + laborId + "> not found")
        );

        RequestDetails details = RequestDetails.builder()
            .request(request)
            .service(service)
            .laboratory(laboratory)
            .isCompleted(false)
            .createdAt(LocalDateTime.now())
            .build();
        detailsRepository.save(details);

        return details;
    }

    @Override
    public List<RequestDetails> addNewList(List<NewRequestDetailsDto> newDetailsList, Request request) {
        return newDetailsList.stream()
            .map(detail -> this.addNew(detail.getServiceId(), detail.getLaborId(), request))
            .toList();
    }

    @Override
    public RequestDetailsDto getById(Long detailsId) {
        RequestDetails details = detailsRepository.findById(detailsId).orElseThrow(
            () -> new NotFoundException("RequestDetails with id <" + detailsId + "> not found")
        );

        return RequestDetailsDto.from(details);
    }

    @Override
    public RequestDetailsDto updateStatusById(Long detailsId, UpdateStatusRequestDetailsDto updateStatus) {
        RequestDetails details = detailsRepository.findById(detailsId).orElseThrow(
            () -> new NotFoundException("RequestDetails with id <" + detailsId + "> not found")
        );

        details.setCompleted(updateStatus.isCompleted());

        detailsRepository.save(details);

        return RequestDetailsDto.from(details);
    }

    @Override
    public void deleteById(Long detailsId) {
        if (detailsRepository.existsById(detailsId)) {
            detailsRepository.deleteById(detailsId);
        } else {
            throw new NotFoundException("RequestDetails with id <" + detailsId + "> not found");
        }
    }
}
