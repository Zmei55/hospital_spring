package com.hospital_spring.address.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewAddressDto {
    @Schema(description = "Street name", example = "Schneekopfstrasse")
    private String street;
    @Schema(description = "House number", example = "15")
    private int houseNumber;
    @Schema(description = "City name", example = "Berlin")
    private String city;
    @Schema(description = "Postal code", example = "12345")
    private int postcode;
}
