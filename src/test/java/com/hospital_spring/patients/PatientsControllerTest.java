package com.hospital_spring.patients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital_spring.address.model.Address;
import com.hospital_spring.address.repositories.AddressesRepository;
import com.hospital_spring.laboratories.repositories.LaboratoriesRepository;
import com.hospital_spring.patients.dto.NewPatientDto;
import com.hospital_spring.patients.dto.PatientFilterDto;
import com.hospital_spring.patients.model.Patient;
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
import java.time.format.DateTimeFormatter;
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
public class PatientsControllerTest {
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

        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);

        List<Patient> emptyPatientList = new ArrayList<>();

        when(addressesRepository.findById(1L)).thenReturn(Optional.of(address));

        when(patientsRepository.findById(1L)).thenReturn(Optional.of(patient));

        when(patientsRepository.findAllByNameContainingIgnoreCaseOrBirthDateOrCardNumber("Ben Weber", null, 0))
            .thenReturn(patientList);

        when(patientsRepository.findAllByNameContainingIgnoreCaseOrBirthDateOrCardNumber(null, null, 0))
            .thenReturn(emptyPatientList);
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void addNew_ReturnsValidResponseEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        NewPatientDto newPatient = new NewPatientDto(
            "Ben Weber",
            "1966-09-12T00:00:00.000+00:00",
            234567891,
            "MALE",
            "+49157456788",
            "benweber@mail.com",
            "ausweis",
            1L
        );

        mockMvc.perform(
                post("/api/patients/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newPatient))
            )
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(201))
            .andExpect(jsonPath("$.message").value("Created"))
            .andExpect(jsonPath("$.data").isNotEmpty())
            .andExpect(jsonPath("$.data.name").value("Ben Weber"))
            .andExpect(jsonPath("$.data.birthDate").value("1966-09-12T00:00:00.000+00:00"))
            .andExpect(jsonPath("$.data.cardNumber").value(234567891))
            .andExpect(jsonPath("$.data.gender").value("MALE"))
            .andExpect(jsonPath("$.data.phoneNumber").value("+49157456788"))
            .andExpect(jsonPath("$.data.email").value("benweber@mail.com"))
            .andExpect(jsonPath("$.data.identityDocument").value("ausweis"))
            .andExpect(jsonPath("$.data.addressId").value(1))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getById_ReturnsValidResponseEntity() throws Exception {
        mockMvc.perform(get("/api/patients/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data").isNotEmpty())
            .andExpect(jsonPath("$.data._id").value(1))
            .andExpect(jsonPath("$.data.name").value("Ben Weber"))
            .andExpect(jsonPath("$.data.birthDate").value("1966-09-12T00:00:00.000+00:00"))
            .andExpect(jsonPath("$.data.cardNumber").value(234567891))
            .andExpect(jsonPath("$.data.gender").value("MALE"))
            .andExpect(jsonPath("$.data.phoneNumber").value("+49157456788"))
            .andExpect(jsonPath("$.data.email").value("benweber@mail.com"))
            .andExpect(jsonPath("$.data.identityDocument").value("ausweis"))
            .andExpect(jsonPath("$.data.address").isNotEmpty())
            .andExpect(jsonPath("$.data.address._id").value(1))
            .andExpect(jsonPath("$.data.address.street").value("Baker Street"))
            .andExpect(jsonPath("$.data.address.houseNumber").value(221))
            .andExpect(jsonPath("$.data.address.city").value("London"))
            .andExpect(jsonPath("$.data.address.postcode").value(56987))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getById_ReturnsNotFoundExceptions() throws Exception {
        String patientId = "2";

        mockMvc.perform(get("/api/patients/" + patientId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Patient with id <" + patientId + "> not found"))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getByFilter_ReturnsValidResponseEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        PatientFilterDto filter = new PatientFilterDto("Ben Weber", null, 0);

        mockMvc.perform(
            post("/api/patients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filter))
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data").isNotEmpty())
            .andExpect(jsonPath("$.data[0]._id").value(1))
            .andExpect(jsonPath("$.data[0].name").value("Ben Weber"))
            .andExpect(jsonPath("$.data[0].birthDate").value("1966-09-12T00:00:00.000+00:00"))
            .andExpect(jsonPath("$.data[0].cardNumber").value(234567891))
            .andExpect(jsonPath("$.data[0].gender").value("MALE"))
            .andExpect(jsonPath("$.data[0].phoneNumber").value("+49157456788"))
            .andExpect(jsonPath("$.data[0].email").value("benweber@mail.com"))
            .andExpect(jsonPath("$.data[0].identityDocument").value("ausweis"))
            .andExpect(jsonPath("$.data[0].addressId").value(1))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getByFilter_ReturnsEmptyList() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        PatientFilterDto filter = new PatientFilterDto(null, null, 0);

        mockMvc.perform(
                post("/api/patients/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(filter))
            )
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Patients not found"))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void updateById_ReturnsValidResponseEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        NewPatientDto newPatient = new NewPatientDto(
            "Bob Weber",
            "1966-09-12T00:00:00.000+00:00",
            234567891,
            "MALE",
            "+49157456788",
            "bobweber@mail.com",
            "ausweis",
            1L
        );

        mockMvc.perform(
                put("/api/patients/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newPatient))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.data").isNotEmpty())
            .andExpect(jsonPath("$.data.name").value("Bob Weber"))
            .andExpect(jsonPath("$.data.birthDate").value("1966-09-12T00:00:00.000+00:00"))
            .andExpect(jsonPath("$.data.cardNumber").value(234567891))
            .andExpect(jsonPath("$.data.gender").value("MALE"))
            .andExpect(jsonPath("$.data.phoneNumber").value("+49157456788"))
            .andExpect(jsonPath("$.data.email").value("bobweber@mail.com"))
            .andExpect(jsonPath("$.data.identityDocument").value("ausweis"))
            .andExpect(jsonPath("$.data.addressId").value(1))
        ;
    }
}
