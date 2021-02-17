package com.sample.pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.jayway.jsonpath.JsonPath;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "departmentProvider", port = "1234")
class EmployeeConsumerTest {

    @Pact(consumer = "employeeConsumer")
    public RequestResponsePact getDepartmentIDFromDepartmentService(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("content-type", "application/json");

        return builder
                .given("valid department ID received from departmentService")
                .uponReceiving("valid department ID from departmentService")
                .method("GET")
                .path("/10")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(LambdaDsl.newJsonBody((object) -> object.numberType("id", 101)).build())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getDepartmentIDFromDepartmentService")
    void shouldBeAbleToGetDepartmentIDFromDepartmentService(MockServer mockServer) throws IOException {
		HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/10")
                .execute().returnResponse();

		assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
        assertThat(JsonPath.read(httpResponse.getEntity().getContent(), "$.id").toString()).hasToString("101");
    }
}
