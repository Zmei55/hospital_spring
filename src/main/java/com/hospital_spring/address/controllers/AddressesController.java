package com.hospital_spring.address.controllers;

import com.hospital_spring.address.controllers.api.AddressesApi;
import com.hospital_spring.address.dto.NewAddressDto;
import com.hospital_spring.address.services.AddressesService;
import com.hospital_spring.shared.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddressesController implements AddressesApi {
    private final AddressesService addressesService;

    @Override
    public ResponseEntity<ResponseDto> addNew(NewAddressDto newAddress) {
        return ResponseEntity.status(HttpStatus.CREATED.value())
            .body(ResponseDto.builder()
                .message("Created")
                .status(HttpStatus.CREATED.value())
                .data(addressesService.addNew(newAddress))
                .build());
    }

    @Override
    public ResponseEntity<ResponseDto> getById(Long addressId) {
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(ResponseDto.builder()
                .message("Successful")
                .status(HttpStatus.OK.value())
                .data(addressesService.getById(addressId))
                .build());
    }

    @Override
    public ResponseEntity<ResponseDto> updateById(Long addressId, NewAddressDto newAddress) {
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(ResponseDto.builder()
                .message("Successful")
                .status(HttpStatus.OK.value())
                .data(addressesService.updateById(addressId, newAddress))
                .build());
    }

    @Override
    public void deleteById(Long addressId) {
        addressesService.deleteById(addressId);
    }
}
