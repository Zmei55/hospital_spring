package com.hospital_spring.address.services.impl;

import com.hospital_spring.address.dto.AddressDto;
import com.hospital_spring.address.dto.NewAddressDto;
import com.hospital_spring.address.model.Address;
import com.hospital_spring.address.repositories.AddressesRepository;
import com.hospital_spring.address.services.AddressesService;
import com.hospital_spring.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AddressesServiceImpl implements AddressesService {
    private final AddressesRepository addressesRepository;

    @Override
    public AddressDto addNew(NewAddressDto newAddress) {
        Address address = Address.builder()
            .street(newAddress.getStreet())
            .houseNumber(newAddress.getHouseNumber())
            .city(newAddress.getCity())
            .postcode(newAddress.getPostcode())
            .createdAt(LocalDateTime.now())
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
    public AddressDto updateById(Long addressId, NewAddressDto newAddress) {
        Address address = addressesRepository.findById(addressId)
            .orElseThrow(
                () -> new NotFoundException("Address with id <" + addressId + "> not found")
            );

        address.setStreet(newAddress.getStreet());
        address.setHouseNumber(newAddress.getHouseNumber());
        address.setCity(newAddress.getCity());
        address.setPostcode(newAddress.getPostcode());

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
