package com.ecom.imgur.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserAccountResponse {

    @JsonProperty("data")
    private UserAccount userDetails;
    private boolean     success;
    private int         status;
}
