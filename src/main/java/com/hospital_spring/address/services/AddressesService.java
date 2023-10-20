package com.hospital_spring.address.services;

import com.hospital_spring.address.dto.AddressDto;
import com.hospital_spring.shared.exceptions.NotFoundException;

public interface AddressesService {
    AddressDto addNew(
        String street,
        int houseNumber,
        String city,
        int postcode
    );

    AddressDto getById(Long addressId);

    AddressDto updateById(
        Long addressId,
        String street,
        int houseNumber,
        String city,
        int postcode
    ) throws NotFoundException;

    void deleteById(Long addressId);
}
