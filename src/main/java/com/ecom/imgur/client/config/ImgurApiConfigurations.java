package com.ecom.imgur.client.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration class for OAuth.
 */

@Configuration
@Data
@NoArgsConstructor
@ConfigurationProperties("imgur.image.api")
public class ImgurApiConfigurations {

    /**
     * The refresh token used for obtaining access tokens.
     */
    private String refreshToken;
    /**
     * The client ID for authentication.
     */
    private String clientId;
    /**
     * The client secrets for authentication.
     */
    private String clientSecrets;
    /**
     * The grant type for obtaining access tokens.
     */
    private String grantType;
    /**
     * The token URI for obtaining access tokens.
     */
    private String tokenUri;
    /**
     * The URI for deleting an image.
     */
    private String deleteImageUri;
    /**
     * The URI for retrieving images.
     */
    private String getImagesUri;
    /**
     * The URI for deleting an image.
     */
    private String uploadImageUri;
    /**
     * The URI for user account details
     */
    private String userAcountUri;

}
