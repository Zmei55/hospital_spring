package com.hospital_spring.address.controllers.api;

import com.hospital_spring.shared.dto.ResponseDto;
import com.hospital_spring.shared.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tags(value = {
    @Tag(name = "Addresses")
})
@RequestMapping("/api/addresses")
public interface AddressesApi {
    @Operation(summary = "Add", description = "Add new address")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "New address",
            content = {
                @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))})})
    @PostMapping("/add")
    ResponseEntity<ResponseDto> addNew(
        @RequestParam String street,
        @RequestParam int houseNumber,
        @RequestParam String city,
        @RequestParam int postcode
    );

    @Operation(summary = "Get by id", description = "Get address by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Address by id",
        content = {
            @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResponseDto.class))})})
    @GetMapping("/{address-id}")
    ResponseEntity<ResponseDto> getById(@PathVariable("address-id") Long addressId) throws NotFoundException;

    @Operation(summary = "Update", description = "Update address by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Update address by id",
        content = {
            @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResponseDto.class))})})
    @PutMapping("/{address-id}")
    ResponseEntity<ResponseDto> updateById(
        @PathVariable("address-id") Long addressId,
        @RequestParam String street,
        @RequestParam int houseNumber,
        @RequestParam String city,
        @RequestParam int postcode
    ) throws NotFoundException;

    @Operation(summary = "Delete", description = "Delete address by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Delete address by id",
        content = {
            @Content(mediaType = "application/json",
            schema = @Schema(implementation = ResponseDto.class))})})
    @DeleteMapping("/{address-id}")
    void deleteById(@PathVariable("address-id") Long addressId) throws NotFoundException;
}
