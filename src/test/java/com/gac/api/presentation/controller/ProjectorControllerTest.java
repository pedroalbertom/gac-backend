package com.gac.api.presentation.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String adminToken;
    private String attendantToken;

    @BeforeEach
    void login() throws Exception {
        adminToken = loginToken("admin", "admin123");
        attendantToken = loginToken("atendente", "atendente123");
    }

    @Test
    void attendantCanCreateProjector() throws Exception {
        mockMvc.perform(post("/api/projectors")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + attendantToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {"brand":"Epson","model":"X123","serialNumber":"SN001","assetTag":"PAT-001"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.assetTag").value("PAT-001"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }

    @Test
    void attendantCannotDeleteProjector() throws Exception {
        MvcResult createResult = mockMvc.perform(post("/api/projectors")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {"brand":"Epson","model":"X200","serialNumber":"SN002","assetTag":"PAT-002"}
                                """))
                .andExpect(status().isCreated())
                .andReturn();

        Number id = JsonPath.read(createResult.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(
                        delete("/api/projectors/" + id.longValue())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + attendantToken))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanListProjectors() throws Exception {
        mockMvc.perform(get("/api/projectors").header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk());
    }

    private String loginToken(String registrationNumber, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {"registrationNumber":"%s","password":"%s"}
                                """
                                        .formatted(registrationNumber, password)))
                .andExpect(status().isOk())
                .andReturn();
        return JsonPath.read(result.getResponse().getContentAsString(), "$.accessToken");
    }
}
