package com.ecom.imgur.client.model;

import lombok.Data;

/**
 * Represents an OAuth token.
 */
@Data
public class OAuthToken {

    /**
     * The refresh token associated with the account.
     */
    private String refresh_token;
    /**
     * The ID of the account.
     */
    private String account_id;
    /**
     * The access token associated with the account.
     */
    private String access_token;
    /**
     * The username of the account.
     */
    private String account_username;
}
