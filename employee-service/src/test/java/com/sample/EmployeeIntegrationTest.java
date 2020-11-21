package com.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = EmployeeApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class EmployeeIntegrationTest {

    final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    protected WireMockServer wireMockServer = new WireMockServer(wireMockConfig().dynamicPort().notifier(
            new ConsoleNotifier(true)));

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        mongoDBContainer.start();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
    }

    @Test
    public void shouldBeAbleToAddEmployeeSuccessfully() throws Exception {
        final Employee employee = Employee.builder().id(1L).name("srini").age(10).build();
        DepartmentResponse departmentResponse = DepartmentResponse.builder().id(101L).name("CSC").build();
        stubFor(get(urlPathMatching("/1"))
                .willReturn(aResponse()
                .withHeader("content-type", "application/json")
                .withBody(new ObjectMapper().writeValueAsString(departmentResponse))));

        mockMvc.perform(post("/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.departmentId").value(employee.getDepartmentId()))
                .andExpect(jsonPath("$.age").value(employee.getAge()));
    }

    @AfterEach
    void tearDown() {
        mongoDBContainer.close();
        dumpInteractions();
        wireMockServer.stop();
    }

    public void dumpInteractions() {
        System.err.println(" >>>> WIREMOCK <<<< ");
        wireMockServer.getAllServeEvents().forEach(event -> System.err.println(event.getRequest().getAbsoluteUrl()));
        System.err.println(" >>>> WIREMOCK <<<< ");
    }
}