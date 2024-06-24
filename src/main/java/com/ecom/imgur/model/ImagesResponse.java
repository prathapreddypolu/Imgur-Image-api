package com.ecom.imgur.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ImagesResponse {

    @JsonProperty("data")
    private List<Image> images;
    private boolean     success;
    private int         status;
}
