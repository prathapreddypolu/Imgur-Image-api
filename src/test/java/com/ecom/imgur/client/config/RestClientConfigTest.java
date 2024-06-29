package com.ecom.imgur.client.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RestClientConfigTest {

    @Autowired
    private RestClient restClient;

    @Test
    public void testRestClient() {
            assertNotNull(restClient);
    }

}