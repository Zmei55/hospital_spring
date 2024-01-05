package com.hospital_spring.laboratories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital_spring.address.model.Address;
import com.hospital_spring.address.repositories.AddressesRepository;
import com.hospital_spring.laboratories.dto.NewLaboratoryDto;
import com.hospital_spring.laboratories.dto.UpdateLaboratoryDto;
import com.hospital_spring.laboratories.model.Laboratory;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LaboratoriesControllerTest {
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
        Address address = Address.builder()
            .id(1L)
            .street("Baker Street")
            .houseNumber(221)
            .city("London")
            .postcode(56987)
            .build();

        List<Laboratory> laboratoryList = new ArrayList<>();
        laboratoryList.add(new Laboratory(1L, "Laboratory_1", true, LocalDateTime.now(), address, new ArrayList<>()));
        laboratoryList.add(new Laboratory(2L, "Laboratory_2", true, LocalDateTime.now(), address, new ArrayList<>()));


        when(addressesRepository.findById(1L)).thenReturn(Optional.of(address));

        when(laboratoriesRepository.findById(1L)).thenReturn(Optional.of(
            Laboratory.builder()
                .id(1L)
                .name("Laboratory")
                .isActive(true)
                .address(address)
                .build()
        ));

        when(laboratoriesRepository.findAllByIsActiveIsTrue()).thenReturn(laboratoryList);
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void addNew_ReturnsValidResponseEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        NewLaboratoryDto newLaboratory = new NewLaboratoryDto("Laboratory", 1L);

        mockMvc.perform(
                post("/api/labors/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newLaboratory))
            )
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(201))
            .andExpect(jsonPath("$.message").value("Created"))
            .andExpect(jsonPath("$.data.name").value("Laboratory"))
            .andExpect(jsonPath("$.data.addressId").value(1))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getById_ReturnsValidResponseEntity() throws Exception {
        mockMvc.perform(get("/api/labors/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data._id").value(1))
            .andExpect(jsonPath("$.data.name").value("Laboratory"))
            .andExpect(jsonPath("$.data.addressId").value(1))
            .andExpect(jsonPath("$.data.active").value(true))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getById_ReturnsNotFoundExceptions() throws Exception {
        String addressId = "2";

        mockMvc.perform(get("/api/labors/" + addressId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Laboratory with id <" + addressId + "> not found"))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getAllActive_ReturnsValidResponseEntity() throws Exception {
        mockMvc.perform(get("/api/labors/"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data").isNotEmpty())
            .andExpect(jsonPath("$.data[0]._id").value(1))
            .andExpect(jsonPath("$.data[0].name").value("Laboratory_1"))
            .andExpect(jsonPath("$.data[0].addressId").value(1))
            .andExpect(jsonPath("$.data[0].active").value(true))
            .andExpect(jsonPath("$.data[1]._id").value(2))
            .andExpect(jsonPath("$.data[1].name").value("Laboratory_2"))
            .andExpect(jsonPath("$.data[1].addressId").value(1))
            .andExpect(jsonPath("$.data[1].active").value(true))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void updateById_ReturnsValidResponseEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UpdateLaboratoryDto newLaboratory = new UpdateLaboratoryDto("Laboratory_2", true);

        mockMvc.perform(
                put("/api/labors/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newLaboratory))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data._id").value(1))
            .andExpect(jsonPath("$.data.name").value("Laboratory_2"))
            .andExpect(jsonPath("$.data.addressId").value(1))
            .andExpect(jsonPath("$.data.active").value(true))
        ;
    }
}
