package com.hospital_spring.address.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital_spring.address.dto.NewAddressDto;
import com.hospital_spring.address.model.Address;
import com.hospital_spring.address.repositories.AddressesRepository;
import com.hospital_spring.laboratories.repositories.LaboratoriesRepository;
import com.hospital_spring.patients.repositories.PatientsRepository;
import com.hospital_spring.requests.repositories.RequestDetailsRepository;
import com.hospital_spring.requests.repositories.RequestsRepository;
import com.hospital_spring.requests.repositories.SearchRequestRepository;
import com.hospital_spring.services.repositories.ServicesRepository;
import com.hospital_spring.test.config.TestConfig;
import com.hospital_spring.users.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AddressesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersRepository usersRepository; // имитируем настоящий репозиторий из проекта

    @MockBean
    private AddressesRepository addressesRepository;

    @MockBean
    private LaboratoriesRepository laboratoriesRepository;

    @MockBean
    private PatientsRepository patientsRepository;

    @MockBean
    private RequestsRepository requestsRepository;

    @MockBean
    private RequestDetailsRepository detailsRepository;

    @MockBean
    private SearchRequestRepository searchRequestRepository;

    @MockBean
    private ServicesRepository servicesRepository;

    @BeforeEach
    void setUp() {
        when(addressesRepository.findById(1L)).thenReturn(Optional.of(
            Address.builder()
                .id(1L)
                .street("Baker Street")
                .houseNumber(221)
                .city("London")
                .postcode(56987)
                .build()
        ));
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void addNewAddress_ReturnsValidResponseEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        NewAddressDto newAddress = new NewAddressDto("Baker Street", 221, "London", 56987);

        mockMvc.perform(
                post("/api/addresses/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newAddress))
            )
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Created"))
            .andExpect(jsonPath("$.data.street").value("Baker Street"))
            .andExpect(jsonPath("$.data.houseNumber").value(221))
            .andExpect(jsonPath("$.data.city").value("London"))
            .andExpect(jsonPath("$.data.postcode").value(56987))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getById_ReturnsValidResponseEntity() throws Exception {
        mockMvc.perform(get("/api/addresses/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data._id").value(1))
            .andExpect(jsonPath("$.data.street").value("Baker Street"))
            .andExpect(jsonPath("$.data.houseNumber").value(221))
            .andExpect(jsonPath("$.data.city").value("London"))
            .andExpect(jsonPath("$.data.postcode").value(56987))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getById_ReturnsNotFoundExceptions() throws Exception {
        String addressId = "2";

        mockMvc.perform(get("/api/addresses/" + addressId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Address with id <" + addressId + "> not found"))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void updateById_ReturnsValidResponseEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        NewAddressDto newAddress = new NewAddressDto("Park Avenue", 200, "New York", 10017);

        mockMvc.perform(
                put("/api/addresses/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newAddress))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data._id").value(1))
            .andExpect(jsonPath("$.data.street").value("Park Avenue"))
            .andExpect(jsonPath("$.data.houseNumber").value(200))
            .andExpect(jsonPath("$.data.city").value("New York"))
            .andExpect(jsonPath("$.data.postcode").value(10017))
        ;
    }
}
