package com.ecom.imgur.client;

import com.ecom.imgur.client.config.ImgurApiConfigurations;
import com.ecom.imgur.client.config.OAuth2Service;
import com.ecom.imgur.common.Constant;
import com.ecom.imgur.exception.ImageAPIException;
import com.ecom.imgur.model.ImageResponse;
import com.ecom.imgur.model.ImagesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImagesClient {

    @Autowired
    private OAuth2Service oAuth2Service;
    private final RestClient             restClient;
    private final ImgurApiConfigurations oAuthConfigurations;

    /**
     *
     *  Uploads an image to the Imgur API.
     *
     * @param  file The image file to be uploaded.
     * @return An instance of ImgurApiResponse containing the response from the API
     * @throws ImageAPIException If the image upload fails.
     */
    public ImageResponse uploadImage(MultipartFile file) {

        return restClient.post()
                .uri(oAuthConfigurations.getUploadImageUri()+"?client_id=", oAuthConfigurations.getClientId())
                .header(Constant.AUTHORIZATION, Constant.BEARER + oAuth2Service.getAccessToken().getAccess_token()    )
                .accept(MediaType.MULTIPART_FORM_DATA)
                .body(file.getResource())
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    String responseBody = StringUtils.toEncodedString(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
                    log.error(" Received 5XX Error for getting Image Details - Response Code {} : Response Body {} ", response.getStatusCode(), responseBody);
                    throw new ImageAPIException(response.getStatusCode().toString(), responseBody);
                })
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    String responseBody = StringUtils.toEncodedString(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
                    log.error(" Received 4XX Error for getting Image Details - Response Code {} : Response Body {} ", response.getStatusCode(), responseBody);
                    throw new ImageAPIException(response.getStatusCode().toString(), responseBody);
                })
                .body(ImageResponse.class);
    }

    /**
     * Retrieves image details for a given user from the Imgur API.
     * @param userName The username of the user whose image details are to be retrieved.
     * @return An instance of ImgurResponse containing the image details.
     */
    public ImagesResponse getImageDetails(String userName)
    {
        return restClient.get()
                .uri(oAuthConfigurations.getGetImagesUri(),userName)
                .header(Constant.AUTHORIZATION, Constant.BEARER + oAuth2Service.getAccessToken().getAccess_token())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    String responseBody = StringUtils.toEncodedString(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
                    log.error(" Received 5XX Error for getting Image Details - Response Code {} : Response Body {} ", response.getStatusCode(), responseBody);
                    throw new ImageAPIException(response.getStatusCode().toString(), responseBody);
                })
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    String responseBody = StringUtils.toEncodedString(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
                    log.error(" Received 4XX Error for getting Image Details - Response Code {} : Response Body {} ", response.getStatusCode(), responseBody);
                    throw new ImageAPIException(response.getStatusCode().toString(), responseBody);
                })
                .body(ImagesResponse.class);
    }

    public String deleteImage(String imageId){

        return  restClient.delete()
                .uri(oAuthConfigurations.getDeleteImageUri(),imageId)
                .header(Constant.AUTHORIZATION, Constant.BEARER + oAuth2Service.getAccessToken().getAccess_token())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    String responseBody = StringUtils.toEncodedString(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
                    log.error(" Received 5XX Error for getting Image Details - Response Code {} : Response Body {} ", response.getStatusCode(), responseBody);
                    throw new ImageAPIException(response.getStatusCode().toString(), responseBody);
                })
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    String responseBody = StringUtils.toEncodedString(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
                    log.error(" Received xXX Error for getting Image Details - Response Code {} : Response Body {} ", response.getStatusCode(), responseBody);
                    throw new ImageAPIException(response.getStatusCode().toString(), responseBody);
                }).toBodilessEntity().getStatusCode().toString();

    }
}
