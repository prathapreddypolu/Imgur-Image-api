package com.ecom.imgur.client;


import com.ecom.imgur.client.config.OAuth2Service;
import com.ecom.imgur.client.model.OAuthToken;
import com.ecom.imgur.common.Constant;
import com.ecom.imgur.exception.ImageAPIException;
import com.ecom.imgur.model.UserAccountResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UserAccountClient.class })
public class UserAccountClientTest {

    @MockBean
    private OAuth2Service oAuth2Service;
    @MockBean
    private RestClient restClient;
    @MockBean
    RestClient.RequestBodyUriSpec requestBodyUriSpec;
    @MockBean
    RestClient.ResponseSpec responseSpec;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    RestClient.RequestBodySpec requestBodySpec;
    @Mock
    UserAccountResponse userAccountResponse;
    @Mock OAuthToken oAuthToken;

    @Autowired
    private UserAccountClient userAccountClient;

    @BeforeEach
    void setUp() {
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class))).thenReturn(requestBodySpec);
        when(oAuth2Service.getAccessToken()).thenReturn(oAuthToken);
        when(oAuthToken.getAccess_token()).thenReturn("access_token");
        when(requestBodySpec.header(Constant.AUTHORIZATION, Constant.BEARER + "access_token")).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.accept(MediaType.APPLICATION_JSON)).thenReturn(requestBodyUriSpec);
    }

    @Test
    void testGetUserAccountInfoWhenSuccess() {
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(),any())).thenReturn(responseSpec);
        when(responseSpec.body(UserAccountResponse.class)).thenReturn(userAccountResponse);

        UserAccountResponse actualResult = userAccountClient.getUserAccountInfo("userAccountName");

        assertNotNull(actualResult);

        verify(restClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri(any(String.class));
        verify(requestBodyUriSpec, times(1)).retrieve();
        verify(responseSpec, times(1)).body(UserAccountResponse.class);
    }

    @Test
    void testGetUserAccountInfoWhen4XXError() {
        when(requestBodyUriSpec.retrieve())
                .thenThrow(new ImageAPIException(HttpStatus.BAD_REQUEST.toString(), "responseBody"));
        when(responseSpec.onStatus(any(),any())).thenReturn(responseSpec);

        assertThrows(ImageAPIException.class, () -> userAccountClient.getUserAccountInfo("userAccountName"));

        verify(restClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri(any(String.class));
        verify(requestBodyUriSpec, times(1)).retrieve();
        verify(responseSpec, times(0)).body(UserAccountResponse.class);
    }

    @Test
    void testGetUserAccountInfoWhen5XXError() {
        when(requestBodyUriSpec.retrieve())
                .thenThrow(new ImageAPIException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "responseBody"));
        when(responseSpec.onStatus(any(),any())).thenReturn(responseSpec);

        assertThrows(ImageAPIException.class, () -> userAccountClient.getUserAccountInfo("userAccountName"));

        verify(restClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri(any(String.class));
        verify(requestBodyUriSpec, times(1)).retrieve();
        verify(responseSpec, times(0)).body(UserAccountResponse.class);
    }
}