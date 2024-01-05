package com.hospital_spring.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital_spring.address.model.Address;
import com.hospital_spring.address.repositories.AddressesRepository;
import com.hospital_spring.laboratories.model.Laboratory;
import com.hospital_spring.laboratories.repositories.LaboratoriesRepository;
import com.hospital_spring.patients.model.Patient;
import com.hospital_spring.patients.repositories.PatientsRepository;
import com.hospital_spring.requests.dto.UpdateStatusRequestDetailsDto;
import com.hospital_spring.requests.model.Request;
import com.hospital_spring.requests.model.RequestDetails;
import com.hospital_spring.requests.repositories.RequestDetailsRepository;
import com.hospital_spring.requests.repositories.RequestsRepository;
import com.hospital_spring.requests.repositories.SearchRequestRepository;
import com.hospital_spring.services.model.Service;
import com.hospital_spring.services.repositories.ServicesRepository;
import com.hospital_spring.test.config.TestConfig;
import com.hospital_spring.users.enums.Position;
import com.hospital_spring.users.enums.Role;
import com.hospital_spring.users.enums.Workplace;
import com.hospital_spring.users.model.User;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RequestDetailsControllerTest {
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
        User user = User.builder()
            .id(1L)
            .username("asd")
            .name("John Smith")
            .role(Role.ADMIN)
            .workplace(Workplace.TREATMENT_ROOM)
            .position(Position.NURSE)
            .isNotLocked(true)
            .token("lkdflsdm.sldfksld.jdfkjdfkj")
            .build();

        Address address = Address.builder()
            .id(1L)
            .street("Baker Street")
            .houseNumber(221)
            .city("London")
            .postcode(56987)
            .build();

        Patient patient = Patient.builder()
            .id(1L)
            .name("Ben Weber")
            .birthDate("1966-09-12T00:00:00.000+00:00")
            .cardNumber(234567891)
            .gender(Patient.Gender.MALE)
            .phoneNumber("+49157456788")
            .email("benweber@mail.com")
            .identityDocument("ausweis")
            .requests(new ArrayList<>())
            .createdAt(LocalDateTime.parse("2023-01-21T23:07:20.779+00:00", DateTimeFormatter.ISO_DATE_TIME))
            .address(address)
            .build();

        Service service = Service.builder()
            .id(1L)
            .name("Immunglobulin Test (IgT)")
            .code("L18.36.00.0.999")
            .isActive(true)
            .build();

        Laboratory laboratory = Laboratory.builder()
            .id(1L)
            .name("Laboratory")
            .isActive(true)
            .address(address)
            .build();

        Request request = Request.builder()
            .id(1L)
            .requestNumber(1L)
            .patient(patient)
            .user(user)
            .isCompleted(false)
            .createdAt(LocalDateTime.now())
            .build();

        RequestDetails details = RequestDetails.builder()
            .id(1L)
            .request(request)
            .service(service)
            .laboratory(laboratory)
            .isCompleted(false)
            .createdAt(LocalDateTime.now())
            .build();

        when(detailsRepository.findById(1L)).thenReturn(Optional.of(details));
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getById_ReturnsValidResponseEntity() throws Exception {
        mockMvc.perform(get("/api/request-details/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data._id").value(1))
            .andExpect(jsonPath("$.data.serviceId").value(1))
            .andExpect(jsonPath("$.data.laborId").value(1))
            .andExpect(jsonPath("$.data.completed").value(false))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getById_ReturnsNotFoundExceptions() throws Exception {
        String detailsId = "2";

        mockMvc.perform(get("/api/request-details/" + detailsId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("RequestDetails with id <" + detailsId + "> not found"))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void updateStatusById_ReturnsValidResponseEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UpdateStatusRequestDetailsDto newStatus = new UpdateStatusRequestDetailsDto(true);

        mockMvc.perform(
                put("/api/request-details/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newStatus))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data._id").value(1))
            .andExpect(jsonPath("$.data.serviceId").value(1))
            .andExpect(jsonPath("$.data.laborId").value(1))
            .andExpect(jsonPath("$.data.completed").value(true))
        ;
    }
}
