package com.hospital_spring.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital_spring.address.repositories.AddressesRepository;
import com.hospital_spring.laboratories.repositories.LaboratoriesRepository;
import com.hospital_spring.patients.repositories.PatientsRepository;
import com.hospital_spring.requests.repositories.RequestDetailsRepository;
import com.hospital_spring.requests.repositories.RequestsRepository;
import com.hospital_spring.requests.repositories.SearchRequestRepository;
import com.hospital_spring.services.dto.FilterServiceDto;
import com.hospital_spring.services.dto.NewServiceDto;
import com.hospital_spring.services.dto.UpdateServiceDto;
import com.hospital_spring.services.model.Service;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ServicesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersRepository usersRepository;

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
        when(servicesRepository.findById(1L)).thenReturn(Optional.of(
            Service.builder()
                .id(1L)
                .name("Immunglobulin Test (IgT)")
                .code("L18.36.00.0.999")
                .isActive(true)
                .build()
        ));
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void addNew_ReturnsValidResponseEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        NewServiceDto newService = new NewServiceDto("Immunglobulin Test (IgT)", "L18.36.00.0.999");

        mockMvc.perform(
                post("/api/services/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newService))
            )
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(201))
            .andExpect(jsonPath("$.message").value("Created"))
            .andExpect(jsonPath("$.data.name").value("Immunglobulin Test (IgT)"))
            .andExpect(jsonPath("$.data.code").value("L18.36.00.0.999"))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getById_ReturnsValidResponseEntity() throws Exception {
        mockMvc.perform(get("/api/services/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data._id").value(1))
            .andExpect(jsonPath("$.data.name").value("Immunglobulin Test (IgT)"))
            .andExpect(jsonPath("$.data.code").value("L18.36.00.0.999"))
            .andExpect(jsonPath("$.data.active").value(true))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getById_ReturnsNotFoundExceptions() throws Exception {
        String addressId = "2";

        mockMvc.perform(get("/api/services/" + addressId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Service with id <" + addressId + "> not found"))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getByFilter_ReturnsValidResponseEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        FilterServiceDto filter = new FilterServiceDto("Immunglobulin");
        List<Service> serviceList = new ArrayList<>();
        serviceList.add(new Service(1L, "Immunglobulin Test (IgT)", "L18.36.00.0.999", true, LocalDateTime.now(), new ArrayList<>()));

        when(servicesRepository.findAllByNameContainingIgnoreCaseAndIsActiveTrue("Immunglobulin"))
            .thenReturn(serviceList);

        mockMvc.perform(
                post("/api/services/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(filter))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data").isNotEmpty())
            .andExpect(jsonPath("$.data[0]._id").value(1))
            .andExpect(jsonPath("$.data[0].name").value("Immunglobulin Test (IgT)"))
            .andExpect(jsonPath("$.data[0].code").value("L18.36.00.0.999"))
            .andExpect(jsonPath("$.data[0].active").value(true))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getByFilter_ReturnsEmptyList() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        FilterServiceDto filter = new FilterServiceDto(null);
        List<Service> serviceList = new ArrayList<>();

        when(servicesRepository.findAllByNameContainingIgnoreCaseAndIsActiveTrue("Immunglobulin"))
            .thenReturn(serviceList);

        mockMvc.perform(
                post("/api/services/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(filter))
            )
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Services not found"))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void updateById_ReturnsValidResponseEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UpdateServiceDto newService = new UpdateServiceDto("Immunglobulin Test_2 (IgT_2)", "L18.36.00.0.999", false);

        mockMvc.perform(
                put("/api/services/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newService))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data._id").value(1))
            .andExpect(jsonPath("$.data.name").value("Immunglobulin Test_2 (IgT_2)"))
            .andExpect(jsonPath("$.data.code").value("L18.36.00.0.999"))
            .andExpect(jsonPath("$.data.active").value(false))
        ;
    }
}
