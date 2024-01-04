package com.hospital_spring.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital_spring.address.repositories.AddressesRepository;
import com.hospital_spring.laboratories.repositories.LaboratoriesRepository;
import com.hospital_spring.patients.repositories.PatientsRepository;
import com.hospital_spring.requests.repositories.RequestDetailsRepository;
import com.hospital_spring.requests.repositories.RequestsRepository;
import com.hospital_spring.requests.repositories.SearchRequestRepository;
import com.hospital_spring.services.repositories.ServicesRepository;
import com.hospital_spring.test.config.TestConfig;
import com.hospital_spring.users.dto.UserUpdateDto;
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

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test") // использовать application-test.properties
class UsersControllerTest {
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
        when(usersRepository.findById(1L)).thenReturn(Optional.of(
            // возвращает пользователя при поиске по id
            User.builder()
                .id(1L)
                .username("asd")
                .name("John Smith")
                .role(Role.ADMIN)
                .workplace(Workplace.TREATMENT_ROOM)
                .position(Position.NURSE)
                .isNotLocked(true)
                .token("lkdflsdm.sldfksld.jdfkjdfkj")
                .build()
        ));

        when(usersRepository.findById(2L)).thenReturn(Optional.of(
            // возвращает пользователя при поиске по id
            User.builder()
                .id(2L)
                .username("qwe")
                .name("Emma")
                .role(Role.ADMIN)
                .workplace(Workplace.TREATMENT_ROOM)
                .position(Position.NURSE)
                .isNotLocked(true)
                .token("lkdflsdm.sldfksld.jdfkjdfkj")
                .build()
        ));
    }

    @WithUserDetails(value = "authenticated user") // если отправили "админ", то вернёт объект пользователя из конфига
    @Test
    void getProfile_ReturnsValidResponseEntity() throws Exception {
        mockMvc.perform(get("/api/users/profile"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.data._id").value(1))
            .andExpect(jsonPath("$.data.name").value("John Smith"))
            .andExpect(jsonPath("$.data.username").value("asd"))
            .andExpect(jsonPath("$.data.role").value("ADMIN"))
            .andExpect(jsonPath("$.data.workplace").value("TREATMENT_ROOM"))
            .andExpect(jsonPath("$.data.position").value("NURSE"))
            .andExpect(jsonPath("$.data.token").value("lkdflsdm.sldfksld.jdfkjdfkj"))
            .andExpect(jsonPath("$.data.notLocked").value(true))
        ;
    }

    @Test
    void unauthenticatedUser() throws Exception {
        mockMvc.perform(get("/api/users/profile")).andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.status").value(401))
            .andExpect(jsonPath("$.message").value("User unauthorized"))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getUserById_ReturnsValidResponseEntity() throws Exception {
        mockMvc.perform(get("/api/users/2"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.data._id").value(2))
            .andExpect(jsonPath("$.data.name").value("Emma"))
            .andExpect(jsonPath("$.data.username").value("qwe"))
            .andExpect(jsonPath("$.data.role").value("USER"))
            .andExpect(jsonPath("$.data.workplace").value("TREATMENT_ROOM"))
            .andExpect(jsonPath("$.data.position").value("NURSE"))
            .andExpect(jsonPath("$.data.token").value("lkdflsdm.sldfksld.jdfkjdfkj"))
            .andExpect(jsonPath("$.data.notLocked").value(true))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void getUserById_ReturnsNotFoundException() throws Exception {
        String userId = "3";

        mockMvc.perform(get("/api/users/" + userId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("User with id <" + userId + "> not found"))
        ;
    }

    @WithUserDetails(value = "authenticated user")
    @Test
    void updateUser_ReturnsValidResponseEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserUpdateDto newUser = new UserUpdateDto("Emma Weber", "TREATMENT_ROOM", "NURSE", true);

        mockMvc.perform(
                put("/api/users/2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newUser))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Successful"))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.data._id").value(2))
            .andExpect(jsonPath("$.data.name").value("Emma Weber"))
            .andExpect(jsonPath("$.data.username").value("qwe"))
            .andExpect(jsonPath("$.data.role").value("ADMIN"))
            .andExpect(jsonPath("$.data.workplace").value("TREATMENT_ROOM"))
            .andExpect(jsonPath("$.data.position").value("NURSE"))
            .andExpect(jsonPath("$.data.token").value("lkdflsdm.sldfksld.jdfkjdfkj"))
            .andExpect(jsonPath("$.data.notLocked").value(true))
        ;
    }
}