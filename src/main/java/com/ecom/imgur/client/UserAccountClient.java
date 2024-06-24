package com.ecom.imgur.client;

import com.ecom.imgur.client.config.OAuth2Service;
import com.ecom.imgur.common.Constant;
import com.ecom.imgur.exception.ImageAPIException;
import com.ecom.imgur.model.UserAccountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountClient {

    @Autowired
    private       OAuth2Service oAuth2Service;
    private final RestClient    restClient;


    /**
     * Retrieves image details from the Imgur API for a specific user account.
     *
     * @return The response containing the user account details.
     * @throws ImageAPIException If there is an error while retrieving the image details.
     */
    public  UserAccountResponse getUserAccountInfo(String userAccountName) {

        return restClient.get()
                .uri("https://api.imgur.com/3/account/"+userAccountName)
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
                .body(UserAccountResponse.class);
    }
}
