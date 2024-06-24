package com.ecom.imgur.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImageResponse {

    private boolean success;
    @JsonProperty("data")
    private Image   imageInfo;
    private String  error;
}
