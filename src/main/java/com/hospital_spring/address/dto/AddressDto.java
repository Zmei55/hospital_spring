package com.hospital_spring.address.dto;

import com.hospital_spring.address.model.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressDto {
    @Schema(description = "id of the address", example = "1")
    private Long _id;
    @Schema(description = "Street name", example = "Schneekopfstrasse")
    private String street;
    @Schema(description = "House number", example = "15")
    private int houseNumber;
    @Schema(description = "City name", example = "Berlin")
    private String city;
    @Schema(description = "Postal code", example = "12345")
    private int postcode;
    @Schema(description = "Creation date")
    private LocalDateTime createdAt;

    public static AddressDto from(Address address) {
        return AddressDto.builder()
            ._id(address.getId())
            .street(address.getStreet())
            .houseNumber(address.getHouseNumber())
            .city(address.getCity())
            .postcode(address.getPostcode())
            .build();
    }
}
