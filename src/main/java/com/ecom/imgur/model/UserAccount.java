package com.ecom.imgur.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserAccount {

    private Integer id;
    private String url;
    private String bio;
    private String avatar;
    @JsonProperty("avatar_name")
    private String avatarName;
    private String cover;
    @JsonProperty("cover_name")
    private String coverName;
    private Float reputation;
    @JsonProperty("reputation_name")
    private String reputationName;
    private String created;
    @JsonProperty("is_blocked")
    private boolean isBlocked;
}
