package com.ecom.imgur.client.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WebSecurityConfigTest {

    @Mock
    private WebSecurityConfig webSecurityConfig;

    @Test
    void testSecurityConfiguration() throws Exception {
        HttpSecurity httpSecurity = mock(HttpSecurity.class);
        webSecurityConfig.securityFilterChain(httpSecurity);
        verify(webSecurityConfig, times(1)).securityFilterChain(any());
    }
}