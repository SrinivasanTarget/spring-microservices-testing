package com.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class EmployeeIntegrationTest {

    final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mongoDBContainer.start();
    }

    @Test
    public void shouldBeAbleToAddEmployeeSuccessfully() throws Exception {
        final Employee employee = Employee.builder().id(1L).departmentId(101L).name("srini").age(10).build();

        mockMvc.perform(post("/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().is2xxSuccessful());
    }

    @AfterEach
    void tearDown() {
        mongoDBContainer.close();
    }
}