package com.hospital_spring.address.services.impl;

import com.hospital_spring.address.dto.AddressDto;
import com.hospital_spring.address.model.Address;
import com.hospital_spring.address.repositories.AddressesRepository;
import com.hospital_spring.address.services.AddressesService;
import com.hospital_spring.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressesServiceImpl implements AddressesService {
    private final AddressesRepository addressesRepository;

    @Override
    public AddressDto addNew(
        String street,
        int houseNumber,
        String city,
        int postcode
    ) {
        Address address = Address.builder()
            .street(street)
            .houseNumber(houseNumber)
            .city(city)
            .postcode(postcode)
            .build();
        addressesRepository.save(address);

        return AddressDto.from(address);
    }

    @Override
    public AddressDto getById(Long addressId) {
        Address address = addressesRepository.findById(addressId)
            .orElseThrow(
                () -> new NotFoundException("Address with id <" + addressId + "> not found")
            );

        return AddressDto.from(address);
    }

    @Override
    public AddressDto updateById(
        Long addressId,
        String street,
        int houseNumber,
        String city,
        int postcode
    ) {
        Address address = addressesRepository.findById(addressId)
            .orElseThrow(
                () -> new NotFoundException("Address with id <" + addressId + "> not found")
            );

        address.setStreet(street);
        address.setHouseNumber(houseNumber);
        address.setCity(city);
        address.setPostcode(postcode);

        addressesRepository.save(address);

        return AddressDto.from(address);
    }

    @Override
    public void deleteById(Long addressId) {
        if (addressesRepository.existsById(addressId)) {
            addressesRepository.deleteById(addressId);
        } else {
            throw new NotFoundException("Address with id <" + addressId + "> not found");
        }
    }
}
