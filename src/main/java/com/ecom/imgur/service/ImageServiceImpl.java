package com.ecom.imgur.service;

import com.ecom.imgur.client.ImagesClient;
import com.ecom.imgur.client.UserAccountClient;
import com.ecom.imgur.model.ImageResponse;
import com.ecom.imgur.model.ImagesResponse;
import com.ecom.imgur.model.UserAccountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImagesClient imageClient;
    @Autowired
    private
    UserAccountClient userAccountClient;
    @Override public
    ImagesResponse getImages(String username) {
        ImagesResponse imageDetail =imageClient.getImageDetails(username);
        System.out.println(" Image Details "+imageDetail.toString());
        return imageDetail;
    }

    @Override
    public
    ImageResponse saveImage(String userName, MultipartFile file) {
        ImageResponse imgurApiResponse=imageClient.UploadImage(file);
        return imgurApiResponse;
    }

    @Override
    public void deleteImage(String imageId) {
        imageClient.deleteImage(imageId);
    }

    @Override
    public
    UserAccountResponse getUserAccountInfo(String userAccountName) {
       return userAccountClient.getUserAccountInfo(userAccountName);
    }

}
