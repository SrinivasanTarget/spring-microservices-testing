package com.sample.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.sample.DepartmentResponse;
import com.sample.Employee;
import com.sample.EmployeeApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = EmployeeApplication.class)
@Testcontainers
@AutoConfigureMockMvc
class EmployeeIntegrationTest {

    @Container
    final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    final WireMockServer wiremockserver = new WireMockServer(wireMockConfig().port(9000)
            .notifier(new ConsoleNotifier(true)));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        wiremockserver.start();
    }

    @Test
    void shouldBeAbleToAddEmployeeSuccessfully() throws Exception {
        final Employee employee = Employee.builder().id(1L).name("srini").age(10).build();
        DepartmentResponse departmentResponse = DepartmentResponse.builder().id(101L).name("CSC").build();
        wiremockserver.stubFor(get(urlPathEqualTo("/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(new ObjectMapper().writeValueAsString(departmentResponse))));

        mockMvc.perform(post("/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.departmentId").value(departmentResponse.getId()))
                .andExpect(jsonPath("$.age").value(employee.getAge()));
    }

    @AfterEach
    void tearDown() {
        dumpInteractions();
        wiremockserver.stop();
    }

    public void dumpInteractions() {
        System.err.println(" >>>> WIREMOCK <<<< ");
        wiremockserver.getAllServeEvents().forEach(event -> System.err.println(event.getRequest().getAbsoluteUrl()));
        System.err.println(" >>>> WIREMOCK <<<< ");
    }
}