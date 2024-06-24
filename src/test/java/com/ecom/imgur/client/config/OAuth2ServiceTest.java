/*
package com.ecom.imgur.client.config;

import com.ecom.imgur.client.model.OAuthToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = { OAuth2Service.class })
public class OAuth2ServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private OAuthConfigurations oAuthConfigurations;

    private OAuth2Service oAuth2Service;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        oAuthConfigurations = new OAuthConfigurations();
        oAuth2Service = new OAuth2Service(restTemplate, oAuthConfigurations);
    }

    @Test
    public void testGetAccessToken() throws OAuth2AuthorizationException {
        // given
        OAuthToken accessToken = new OAuthToken();
        String tokenUri = "https://api.imgur.com/oauth2/token";
        oAuthConfigurations.setTokenUri(tokenUri);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity<OAuthToken> responseEntity = new ResponseEntity<>(accessToken, HttpStatus.OK);

        when(restTemplate.exchange(eq(tokenUri), eq(HttpMethod.POST), any(HttpEntity.class), eq(OAuthToken.class))).thenReturn(responseEntity);

        OAuthToken result = oAuth2Service.getAccessToken();
        // then
        assertEquals(result, accessToken);
    }
}*/
