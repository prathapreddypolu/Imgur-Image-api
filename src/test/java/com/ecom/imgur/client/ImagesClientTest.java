package com.ecom.imgur.client;

import com.ecom.imgur.client.config.OAuth2Service;
import com.ecom.imgur.client.model.OAuthToken;
import com.ecom.imgur.common.Constant;
import com.ecom.imgur.exception.ImageAPIException;
import com.ecom.imgur.model.ImageResponse;
import com.ecom.imgur.model.ImagesResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ImagesClientTest {
    private static final String ACCESS_TOKEN = "access_token";
    @MockBean
    private OAuth2Service oAuth2Service;
    @MockBean
    private RestClient restClient;
    @MockBean
    private RestClient.RequestBodyUriSpec requestBodyUriSpec;
    @MockBean
    private RestClient.ResponseSpec responseSpec;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private RestClient.RequestBodySpec requestBodySpec;
    @Mock
    private OAuthToken oAuthToken;

    @Mock
    private ImagesResponse imagesResponse;
    @Mock
    private ImageResponse imageResponse;
    @Mock
    private MultipartFile multipartFile;
    @Mock
    private Resource fileResource;

    @Autowired
    private ImagesClient imagesClient;

    @Test
    void testGetImageDetailsWhenSuccess() {
        when(oAuth2Service.getAccessToken()).thenReturn(oAuthToken);
        when(oAuthToken.getAccess_token()).thenReturn(ACCESS_TOKEN);
        when(requestBodySpec.header(Constant.AUTHORIZATION, Constant.BEARER + "access_token")).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.accept(MediaType.APPLICATION_JSON)).thenReturn(requestBodyUriSpec);
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class),any(String.class))).thenReturn(requestBodySpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(),any())).thenReturn(responseSpec);
        when(responseSpec.body(ImagesResponse.class)).thenReturn(imagesResponse);

        ImagesResponse actualResult = imagesClient.getImageDetails("userName");

        assertNotNull(actualResult);

        verify(restClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri(any(String.class), any(String.class));
        verify(requestBodyUriSpec, times(1)).retrieve();
        verify(responseSpec, times(1)).body(ImagesResponse.class);
    }
    @Test
    void testGetImageDetailsWhen4XXError() {
        when(oAuth2Service.getAccessToken()).thenReturn(oAuthToken);
        when(oAuthToken.getAccess_token()).thenReturn(ACCESS_TOKEN);
        when(requestBodySpec.header(Constant.AUTHORIZATION, Constant.BEARER + "access_token")).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.accept(MediaType.APPLICATION_JSON)).thenReturn(requestBodyUriSpec);
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class),any(String.class))).thenReturn(requestBodySpec);
        when(requestBodyUriSpec.retrieve())
                .thenThrow(new ImageAPIException("400", "responseBody"));
        when(responseSpec.onStatus(any(),any())).thenReturn(responseSpec);

        assertThrows(ImageAPIException.class, () -> imagesClient.getImageDetails("userName"));

        verify(restClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri(any(String.class), any(String.class));
        verify(requestBodyUriSpec, times(1)).retrieve();
        verify(responseSpec, times(0)).body(ImagesResponse.class);
    }

    @Test
    void testGetImageDetailsWhen5XXError() {
        when(oAuth2Service.getAccessToken()).thenReturn(oAuthToken);
        when(oAuthToken.getAccess_token()).thenReturn(ACCESS_TOKEN);
        when(requestBodySpec.header(Constant.AUTHORIZATION, Constant.BEARER + "access_token")).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.accept(MediaType.APPLICATION_JSON)).thenReturn(requestBodyUriSpec);
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class),any(String.class))).thenReturn(requestBodySpec);
        when(requestBodyUriSpec.retrieve())
                .thenThrow(new ImageAPIException("500", "responseBody"));
        when(responseSpec.onStatus(any(),any())).thenReturn(responseSpec);

        assertThrows(ImageAPIException.class, () -> imagesClient.getImageDetails("userAccountName"));

        verify(restClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri(any(String.class), any(String.class));
        verify(requestBodyUriSpec, times(1)).retrieve();
        verify(responseSpec, times(0)).body(ImagesResponse.class);
    }

    @Test
    void testDeleteImageDetailsWhenSuccess() {
        when(restClient.delete()).thenReturn(requestHeadersUriSpec);
        // when(imgurApiConfigurations.getDeleteImageUri()).thenReturn("ImageUri");
        when(oAuth2Service.getAccessToken()).thenReturn(oAuthToken);
        when(oAuthToken.getAccess_token()).thenReturn(ACCESS_TOKEN);
        when(requestHeadersUriSpec.uri(any(String.class), any(String.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.header(any(String.class), any(String.class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.accept(any(MediaType.class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(),any())).thenReturn(responseSpec);
        when(responseSpec.toBodilessEntity()).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        String result = imagesClient.deleteImage("imageId");

        assertNotNull(result);
        assertEquals(HttpStatus.OK.toString(), result.toString());
        verify(restClient, times(1)).delete();
        verify(requestHeadersUriSpec, times(1)).uri(any(String.class), any(String.class));
        verify(requestBodyUriSpec, times(1)).retrieve();
        verify(responseSpec, times(1)).toBodilessEntity();
    }
    @Test
    void testDeleteImageDetailsWhen4XXError() {
        when(restClient.delete()).thenReturn(requestHeadersUriSpec);
        when(oAuth2Service.getAccessToken()).thenReturn(oAuthToken);
        when(oAuthToken.getAccess_token()).thenReturn(ACCESS_TOKEN);
        when(requestHeadersUriSpec.uri(any(String.class), any(String.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.header(any(String.class), any(String.class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.accept(any(MediaType.class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(),any())).thenThrow(new ImageAPIException("400", "responseBody"));

        assertThrows(ImageAPIException.class, () -> imagesClient.deleteImage("imageId"));

        verify(restClient, times(1)).delete();
        verify(requestHeadersUriSpec, times(1)).uri(any(String.class), any(String.class));
        verify(requestBodyUriSpec, times(1)).retrieve();
        verify(responseSpec, times(0)).toBodilessEntity();
    }
    @Test
    void testDeleteImageDetailsWhen5XXError() {
        when(restClient.delete()).thenReturn(requestHeadersUriSpec);
        when(oAuth2Service.getAccessToken()).thenReturn(oAuthToken);
        when(oAuthToken.getAccess_token()).thenReturn(ACCESS_TOKEN);
        when(requestHeadersUriSpec.uri(any(String.class), any(String.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.header(any(String.class), any(String.class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.accept(any(MediaType.class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(),any())).thenThrow(new ImageAPIException("500", "responseBody"));

        assertThrows(ImageAPIException.class, () -> imagesClient.deleteImage("imageId"));

        verify(restClient, times(1)).delete();
        verify(requestHeadersUriSpec, times(1)).uri(any(String.class), any(String.class));
        verify(requestBodyUriSpec, times(1)).retrieve();
        verify(responseSpec, times(0)).toBodilessEntity();
    }

    @Test
    void testUploadImageDetailsWhenSuccess() {
        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(oAuth2Service.getAccessToken()).thenReturn(oAuthToken);
        when(oAuthToken.getAccess_token()).thenReturn(ACCESS_TOKEN);
        when(requestBodyUriSpec.uri(any(String.class),any(String.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.header(any(String.class), any(String.class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.accept(any())).thenReturn(requestBodyUriSpec);
        when(multipartFile.getResource()).thenReturn(fileResource);
        when(requestBodyUriSpec.body(fileResource)).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(),any())).thenReturn(responseSpec);
        when(responseSpec.body(ImageResponse.class)).thenReturn(imageResponse);

        ImageResponse actualResult = imagesClient.uploadImage(multipartFile);

        assertNotNull(actualResult);

        verify(restClient, times(1)).post();
        verify(requestBodyUriSpec, times(1)).uri(any(String.class), any(String.class));
        verify(requestBodySpec, times(1)).retrieve();
        verify(responseSpec, times(1)).body(ImageResponse.class);
    }

    @Test
    void testUploadImageDetailsWhen4XXError() {
        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(oAuth2Service.getAccessToken()).thenReturn(oAuthToken);
        when(oAuthToken.getAccess_token()).thenReturn(ACCESS_TOKEN);
        when(requestBodyUriSpec.uri(any(String.class),any(String.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.header(any(String.class), any(String.class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.accept(any())).thenReturn(requestBodyUriSpec);
        when(multipartFile.getResource()).thenReturn(fileResource);
        when(requestBodyUriSpec.body(fileResource)).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenThrow(new ImageAPIException(HttpStatusCode.valueOf(400).toString(), "responseBody"));

        assertThrows(ImageAPIException.class, () -> imagesClient.uploadImage(multipartFile));

        verify(restClient, times(1)).post();
        verify(requestBodyUriSpec, times(1)).uri(any(String.class), any(String.class));
        verify(requestBodySpec, times(1)).retrieve();
        verify(responseSpec, times(0)).body(ImageResponse.class);
    }
    @Test
    void testUploadImageDetailsWhen5XXError() {

        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(oAuth2Service.getAccessToken()).thenReturn(oAuthToken);
        when(oAuthToken.getAccess_token()).thenReturn(ACCESS_TOKEN);
        when(requestBodyUriSpec.uri(any(String.class),any(String.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.header(any(String.class), any(String.class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.accept(any())).thenReturn(requestBodyUriSpec);
        when(multipartFile.getResource()).thenReturn(fileResource);
        when(requestBodyUriSpec.body(fileResource)).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenThrow(new ImageAPIException(HttpStatusCode.valueOf(500).toString(), "responseBody"));

        assertThrows(ImageAPIException.class, () -> imagesClient.uploadImage(multipartFile));

        verify(restClient, times(1)).post();
        verify(requestBodyUriSpec, times(1)).uri(any(String.class), any(String.class));
        verify(requestBodySpec, times(1)).retrieve();
        verify(responseSpec, times(0)).body(ImageResponse.class);
    }

}