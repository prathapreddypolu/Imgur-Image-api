package com.ecom.imgur.client.config;

import com.ecom.imgur.client.model.OAuthToken;
import com.ecom.imgur.common.Constant;
import com.ecom.imgur.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Service class for OAuth2 authentication.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2Service {

    private final RestTemplate           restTemplate;
    private final ImgurApiConfigurations oAuthConfigurations;

    /**
     * Generates an access token using OAuth.
     *
     * @return The access token.
     */
   public OAuthToken getAccessToken() {

       OAuthToken accessTokenResponse;

       MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
       formParams.add(Constant.GRANT_TYPE, oAuthConfigurations.getGrantType());
       formParams.add(Constant.CLIENT_ID, oAuthConfigurations.getClientId());
       formParams.add(Constant.CLIENT_SECRET, oAuthConfigurations.getClientSecrets());
       formParams.add(Constant.REFRESH_TOKEN, oAuthConfigurations.getRefreshToken());

       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.MULTIPART_FORM_DATA);
       HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formParams, headers);

       try {
           accessTokenResponse = restTemplate.exchange(oAuthConfigurations.getTokenUri(), HttpMethod.POST, requestEntity, OAuthToken.class).getBody();
           Assert.notNull(accessTokenResponse, () -> "Access token is generated for : '" + oAuthConfigurations.getClientId());
           log.debug(" oAuth Token details {}",accessTokenResponse.getAccess_token());
           return accessTokenResponse;

       } catch (OAuth2AuthorizationException ex) {
           throw new ApiException(Constant.OAUTH_ERROR_CODE,"OAuth2AuthorizationException",ex);
       }
   }
}