package com.hospital_spring.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital_spring.address.model.Address;
import com.hospital_spring.address.repositories.AddressesRepository;
import com.hospital_spring.laboratories.model.Laboratory;
import com.hospital_spring.laboratories.repositories.LaboratoriesRepository;
import com.hospital_spring.patients.model.Patient;
import com.hospital_spring.patients.repositories.PatientsRepository;
import com.hospital_spring.requests.dto.*;
import com.hospital_spring.requests.model.Request;
import com.hospital_spring.requests.model.RequestDetails;
import com.hospital_spring.requests.model.SearchRequest;
import com.hospital_spring.requests.repositories.RequestDetailsRepository;
import com.hospital_spring.requests.repositories.RequestsRepository;
import com.hospital_spring.requests.repositories.SearchRequestRepository;
import com.hospital_spring.services.dto.FilterServiceDto;
import com.hospital_spring.services.dto.NewServiceDto;
import com.hospital_spring.services.dto.UpdateServiceDto;
import com.hospital_spring.services.model.Service;
import com.hospital_spring.services.repositories.ServicesRepository;
import com.hospital_spring.test.config.TestConfig;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RequestsControllerTest {
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
            .role(User.Role.ADMIN)
            .workplace(User.Workplace.SURGERY__TREATMENT_ROOM)
            .position("nurse")
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

//        RequestDetails details = RequestDetails.builder()
//            .id(1L)
//            .service(service)
//            .laboratory(laboratory)
//            .isCompleted(false)
//            .createdAt(LocalDateTime.now())
//            .build();

        Request request = Request.builder()
            .id(1L)
            .requestNumber(1L)
            .patient(patient)
            .user(user)
            .isCompleted(false)
            .createdAt(LocalDateTime.now())
            .build();

        SearchRequest searchRequest = SearchRequest.builder()
            .id(1L)
            .patientName("Ben Weber")
            .cardNumber(234567891)
            .request(request)
            .requestNumber(1L)
            .createdAt(LocalDateTime.now())
            .requestCreatedAt(LocalDateTime.now())
            .build();

        List<SearchRequest> searchRequestList = new ArrayList<>();
        searchRequestList.add(searchRequest);

        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));

        when(patientsRepository.findById(1L)).thenReturn(Optional.of(patient));

        when(servicesRepository.findById(1L)).thenReturn(Optional.of(service));

        when(laboratoriesRepository.findById(1L)).thenReturn(Optional.of(laboratory));

        when(requestsRepository.findById(1L)).thenReturn(Optional.of(request));

        when(searchRequestRepository.findAllByPatientNameContainingIgnoreCaseOrCardNumberOrRequestNumberOrCreatedAt(
            "Ben", 0, null, null
        )).thenReturn(searchRequestList);
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void addNew_ReturnsValidResponseEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        NewRequestDetailsDto newDetails = new NewRequestDetailsDto(1L, 1L);

        List<NewRequestDetailsDto> detailsList = new ArrayList<>();
        detailsList.add(newDetails);

        NewRequestDto newRequest = new NewRequestDto(1L, 1L, detailsList);

        mockMvc.perform(
                post("/api/requests/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newRequest))
            )
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(201))
            .andExpect(jsonPath("$.message").value("Created"))
            .andExpect(jsonPath("$.data.requestNumber").value(1))
            .andExpect(jsonPath("$.data.patientId").value(1))
            .andExpect(jsonPath("$.data.requestDetails[0].serviceId").value(1))
            .andExpect(jsonPath("$.data.requestDetails[0].laborId").value(1))
            .andExpect(jsonPath("$.data.requestDetails[0].completed").value(false))
            .andExpect(jsonPath("$.data.owner").value(1))
            .andExpect(jsonPath("$.data.completed").value(false))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getById_ReturnsValidResponseEntity() throws Exception {
        mockMvc.perform(get("/api/requests/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data._id").value(1))
            .andExpect(jsonPath("$.data.requestNumber").value(1))
            .andExpect(jsonPath("$.data.patientId").value(1))
            .andExpect(jsonPath("$.data.requestDetails").isEmpty())
            .andExpect(jsonPath("$.data.owner").value(1))
            .andExpect(jsonPath("$.data.completed").value(false))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getById_ReturnsNotFoundExceptions() throws Exception {
        String requestId = "2";

        mockMvc.perform(get("/api/requests/" + requestId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Request with id <" + requestId + "> not found"))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getByFilter_ReturnsValidResponseEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        FilterRequestDto filter = new FilterRequestDto("Ben", 0, null, null);

        mockMvc.perform(
                post("/api/requests/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(filter))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data").isNotEmpty())
            .andExpect(jsonPath("$.data[0]._id").value(1))
            .andExpect(jsonPath("$.data[0].patientName").value("Ben Weber"))
            .andExpect(jsonPath("$.data[0].cardNumber").value(234567891))
            .andExpect(jsonPath("$.data[0].requestId").value(1))
            .andExpect(jsonPath("$.data[0].requestNumber").value(1))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getByFilter_ReturnsEmptyList() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        FilterRequestDto filter = new FilterRequestDto(null, 0, null, null);

        List<SearchRequest> searchRequestList = new ArrayList<>();

        when(searchRequestRepository.findAllByPatientNameContainingIgnoreCaseOrCardNumberOrRequestNumberOrCreatedAt(
            null, 0, null, null
        )).thenReturn(searchRequestList);

        mockMvc.perform(
                post("/api/requests/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(filter))
            )
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Requests not found"))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getRequestsDBCount_ReturnsValidResponseEntity() throws Exception {
        when(requestsRepository.findTopByOrderByIdDesc()).thenReturn(1L);

        mockMvc.perform(post("/api/requests/count"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data").isNotEmpty())
            .andExpect(jsonPath("$.data").value(1))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void updateStatusById_ReturnsValidResponseEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UpdateStatusRequestDto newStatus = new UpdateStatusRequestDto(true);

        mockMvc.perform(
                put("/api/requests/1")
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
