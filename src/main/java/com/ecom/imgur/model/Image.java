package com.ecom.imgur.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Image {

    private String       id;
    private String       title;
    private String       description;
    private long         datetime;
    private String       type;
    private boolean      animated;
    private int          width;
    private int height;
    private int size;
    private int views;
    private long bandwidth;
    private Object vote;
    private boolean favorite;
    private Object nsfw;
    private Object section;
    @JsonProperty("account_url")
    private String accountUrl;
    @JsonProperty("account_id")
    private int accountId;
    @JsonProperty("is_ad")
    private boolean isAd;
    @JsonProperty("in_most_viral")
    private boolean inMostViral;
    @JsonProperty("has_sound")
    private boolean      hasSound;
    private List<String> tags;
    private int          ad_type;
    private String ad_url;
    private String edited;
    @JsonProperty("in_gallery")
    private boolean inGallery;
    private String deletehash;
    private String name;
    private String link;
}
