package com.ecom.imgur.client.config;

import com.ecom.imgur.client.model.OAuthToken;
import com.ecom.imgur.common.Constant;
import com.ecom.imgur.exception.ApiException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {OAuth2Service.class})
@ActiveProfiles("test")
public class OAuth2ServiceTest {

    @Autowired
    private OAuth2Service oauth2Service;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private ImgurApiConfigurations apiConfigurations;

    @Mock
    ResponseEntity responseEntity;

    @Test
    void getAccessToken_shouldReturnAccessToken_whenApiReturnsToken() {
        OAuthToken expectedTokenResponse = new OAuthToken();
        expectedTokenResponse.setAccess_token("24a594a9eae1a1618ccfa368a491e22e08341269");

        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add(Constant.GRANT_TYPE, apiConfigurations.getGrantType());
        formParams.add(Constant.CLIENT_ID, apiConfigurations.getClientId());
        formParams.add(Constant.CLIENT_SECRET, apiConfigurations.getClientSecrets());
        formParams.add(Constant.REFRESH_TOKEN, apiConfigurations.getRefreshToken());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formParams, headers);

        when(restTemplate.exchange(apiConfigurations.getTokenUri(), HttpMethod.POST, requestEntity,OAuthToken.class)).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(expectedTokenResponse);

        OAuthToken response = oauth2Service.getAccessToken();

        assertThat(response).isNotNull();
        assertThat(response.getAccess_token()).isEqualTo(expectedTokenResponse.getAccess_token());
    }

    @Test
    void getAccessToken_shouldThrowException_whenApiReturnsError() {
        OAuthToken expectedTokenResponse = new OAuthToken();
        expectedTokenResponse.setAccess_token("24a594a9eae1a1618ccfa368a491e22e08341269");

        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add(Constant.GRANT_TYPE, apiConfigurations.getGrantType());
        formParams.add(Constant.CLIENT_ID, apiConfigurations.getClientId());
        formParams.add(Constant.CLIENT_SECRET, apiConfigurations.getClientSecrets());
        formParams.add(Constant.REFRESH_TOKEN, apiConfigurations.getRefreshToken());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formParams, headers);

        when(restTemplate.exchange(apiConfigurations.getTokenUri(), HttpMethod.POST, requestEntity,OAuthToken.class)).thenThrow(OAuth2AuthorizationException.class);

        assertThrows(ApiException.class,()->oauth2Service.getAccessToken());

    }

}