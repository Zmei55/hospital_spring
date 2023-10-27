package com.hospital_spring.address.services;

import com.hospital_spring.address.dto.AddressDto;
import com.hospital_spring.address.dto.NewAddressDto;

public interface AddressesService {
    AddressDto addNew(NewAddressDto newAddress);

    AddressDto getById(Long addressId);

    AddressDto updateById(Long addressId, NewAddressDto newAddress);

    void deleteById(Long addressId);
}
