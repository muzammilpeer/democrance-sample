package com.muzammilpeer.democrance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.muzammilpeer.democrance.entity.CustomerEntity;
import com.muzammilpeer.democrance.model.JwtRequest;
import com.muzammilpeer.democrance.model.JwtResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;


@SpringBootTest(classes = DemocranceApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTests
{
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAddCustomer() {
        //Get token from authenticae api first so that create customer api will work
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("user1"); //hardcoded username and password for testing
        jwtRequest.setPassword("password");


        ResponseEntity<JwtResponse> responseEntity1 = this.restTemplate
                .postForEntity("http://localhost:" + port + "/authenticate", jwtRequest, JwtResponse.class);
        //if authentication pass
        assertEquals(200, responseEntity1.getStatusCodeValue());

        String token = "Bearer " + responseEntity1.getBody().getToken();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", token);

        //Create customer information uniquely based on all below 3 param. test failed if in db already same record with combination exist
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFirstName("firstname2");
        customerEntity.setLastName("lastname");
        customerEntity.setDob("25-06-1993");
        HttpEntity<CustomerEntity> entity = new HttpEntity<>(customerEntity, headers);
        ResponseEntity<CustomerEntity> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/customer", entity, CustomerEntity.class);

        //if create customer passed 200, else failed with 500
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

}