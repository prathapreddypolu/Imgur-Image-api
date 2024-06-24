package com.ecom.imgur.client;

import com.ecom.imgur.client.config.ImgurApiConfigurations;
import com.ecom.imgur.common.Constant;
import com.ecom.imgur.client.config.OAuth2Service;
import com.ecom.imgur.exception.ImageAPIException;
import com.ecom.imgur.model.ImageResponse;
import com.ecom.imgur.model.ImagesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
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
    public
    ImageResponse UploadImage(MultipartFile file) {

        log.info("Upload Image file name " + file.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add(Constant.AUTHORIZATION, Constant.BEARER + oAuth2Service.getAccessToken().getAccess_token());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", file.getResource());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<ImageResponse> responseEntity = new RestTemplate().exchange(oAuthConfigurations.getUploadImageUri() + "?client_id=" + oAuthConfigurations.getClientId(),
                    HttpMethod.POST, requestEntity, ImageResponse.class);

            ImageResponse imgurApiResponse = responseEntity.getBody();
            log.debug(" ***** Successfully upload Image Account Url {}", imgurApiResponse.getImageInfo().getAccountUrl());
            if (imgurApiResponse != null && imgurApiResponse.isSuccess()) {
                return imgurApiResponse;
            } else {
                log.error(" Exception while uploading the Image - Response Code {} : Response Body {} ", responseEntity.getStatusCode(), responseEntity.getBody());
                throw new ImageAPIException(responseEntity.getStatusCode().toString(), responseEntity.getBody().toString());
            }
        } catch (HttpClientErrorException ex) {
            throw new ImageAPIException("ERROR_CODE","Image upload failed: " + ex.getMessage());
        }
    }

    /**
     * Retrieves image details for a given user from the Imgur API.
     * @param userName The username of the user whose image details are to be retrieved.
     * @return An instance of ImgurResponse containing the image details.
     */
    public
    ImagesResponse getImageDetails(String userName)
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
                    log.error(" Received xXX Error for getting Image Details - Response Code {} : Response Body {} ", response.getStatusCode(), responseBody);
                    throw new ImageAPIException(response.getStatusCode().toString(), responseBody);
                })
                .body(ImagesResponse.class);
    }

    public void deleteImage(String imageId){

        restClient.delete()
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
                })
                .toBodilessEntity();
    }
}
