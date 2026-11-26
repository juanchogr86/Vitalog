package com.vitalog;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitalog.model.UserAccount;
import com.vitalog.repository.HabitRepository;
import com.vitalog.repository.UserAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
class AuthIntegrationTests {

    private static final String VALID_PASSWORD = "StrongPass1!";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final List<String> createdEmails = new ArrayList<>();

    @AfterEach
    void removeTestUsers() {
        createdEmails.forEach(email -> {
            userAccountRepository.findByEmail(email).ifPresent(account ->
                    habitRepository.deleteByUserId(account.getId()));
            userAccountRepository.deleteByEmail(email);
        });
    }

    @Test
    void registrationStoresBcryptHashAndStartsAuthenticatedSession() throws Exception {
        String email = uniqueEmail();
        MvcResult registration = register(email, VALID_PASSWORD)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is(email)))
                .andReturn();

        UserAccount account = userAccountRepository.findByEmail(email).orElseThrow();
        assertNotEquals(VALID_PASSWORD, account.getPasswordHash());
        assertTrue(account.getPasswordHash().startsWith("$2"));
        assertTrue(passwordEncoder.matches(VALID_PASSWORD, account.getPasswordHash()));

        MockHttpSession session = (MockHttpSession) registration.getRequest().getSession(false);
        assertNotNull(session);
        mockMvc.perform(get("/api/auth/me").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(email)));

        mockMvc.perform(post("/api/auth/logout").with(csrf()).session(session))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/auth/me").session(session))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginAcceptsValidCredentialsAndRejectsInvalidCredentials() throws Exception {
        String email = uniqueEmail();
        register(email, VALID_PASSWORD).andExpect(status().isCreated());

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(email, "wrong-password")))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message", is("El correo o la contraseña no son correctos.")));

        MvcResult login = mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(email, VALID_PASSWORD)))
                .andExpect(status().isOk())
                .andReturn();
        MockHttpSession session = (MockHttpSession) login.getRequest().getSession(false);
        assertNotNull(session);
        mockMvc.perform(get("/api/auth/me").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(email)));
    }

    @Test
    void registrationRejectsWeakPasswordsAndDuplicateEmail() throws Exception {
        String email = uniqueEmail();
        register(email, "weakpass").andExpect(status().isBadRequest());
        register(email, VALID_PASSWORD).andExpect(status().isCreated());
        register(email, VALID_PASSWORD)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("Ya existe una cuenta con ese correo electrónico.")));
    }

    @Test
    void authenticatedUserCanCreateDailyHabitWithDefaultDurationAndStartDate() throws Exception {
        String email = uniqueEmail();
        MvcResult registration = register(email, VALID_PASSWORD).andExpect(status().isCreated()).andReturn();
        MockHttpSession session = (MockHttpSession) registration.getRequest().getSession(false);

        mockMvc.perform(post("/api/habits")
                    .with(csrf())
                    .session(session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(
                            new HabitCredentials("Leer", null, "DAILY", List.of()))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is("Leer")))
            .andExpect(jsonPath("$.durationDays", is(66)))
            .andExpect(jsonPath("$.frequency", is("DAILY")))
            .andExpect(jsonPath("$.startDate").exists());
    }

    @Test
    void authenticatedUserCanCreateWeeklyHabitAndMissingDaysAreRejected() throws Exception {
        String email = uniqueEmail();
        MvcResult registration = register(email, VALID_PASSWORD).andExpect(status().isCreated()).andReturn();
        MockHttpSession session = (MockHttpSession) registration.getRequest().getSession(false);

        mockMvc.perform(post("/api/habits")
                    .with(csrf())
                    .session(session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(
                            new HabitCredentials("Correr", 30, "WEEKLY", List.of()))))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", is(
                    "Selecciona al menos un día para la frecuencia semanal.")));

        mockMvc.perform(post("/api/habits")
                    .with(csrf())
                    .session(session)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(
                            new HabitCredentials("Correr", 30, "WEEKLY",
                                    List.of("MONDAY", "WEDNESDAY", "FRIDAY")))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.durationDays", is(30)))
            .andExpect(jsonPath("$.frequency", is("WEEKLY")))
            .andExpect(jsonPath("$.weeklyDays.length()", is(3)));
    }

    @Test
    void unauthenticatedUserCannotCreateHabit() throws Exception {
        mockMvc.perform(post("/api/habits")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(
                            new HabitCredentials("Leer", 66, "DAILY", List.of()))))
            .andExpect(status().isUnauthorized());
    }

    private org.springframework.test.web.servlet.ResultActions register(String email, String password)
            throws Exception {
        createdEmails.add(email);
        return mockMvc.perform(post("/api/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(email, password)));
    }

    private String json(String email, String password) throws Exception {
        return objectMapper.writeValueAsString(new Credentials(email, password));
    }

    private String uniqueEmail() {
        return "user-" + UUID.randomUUID() + "@example.com";
    }

    private record Credentials(String email, String password) {
    }

    private record HabitCredentials(
            String name,
            Integer durationDays,
            String frequency,
            List<String> weeklyDays) {
    }
}
