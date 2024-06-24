package com.ecom.imgur.service;

import com.ecom.imgur.model.ImageResponse;
import com.ecom.imgur.model.ImagesResponse;
import com.ecom.imgur.model.UserAccountResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImagesResponse getImages(String username);

    ImageResponse saveImage(MultipartFile file);

    void deleteImage(String imageId);

    UserAccountResponse getUserAccountInfo(String userAccountName);

}
