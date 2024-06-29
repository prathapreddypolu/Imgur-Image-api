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
    private UserAccountClient userAccountClient;

    /**
     *
     * @param username
     * @return ImagesResponse response object
     */
    @Override
    public ImagesResponse getImages(String username) {
        ImagesResponse imageDetail =imageClient.getImageDetails(username);
        System.out.println(" Image Details "+imageDetail.toString());
        return imageDetail;
    }

    /**
     *
     * @param file
     * @return ImagesResponse response object
     */
    @Override
    public ImageResponse saveImage( MultipartFile file) {
        ImageResponse imgurApiResponse=imageClient.uploadImage(file);
        return imgurApiResponse;
    }
    /**
     *
     * @param imageId
     * @return deleted Image ID
     */
    @Override
    public String deleteImage(String imageId) {

        return imageClient.deleteImage(imageId);
    }

    /**
     *
     * @param userAccountName
     * @return userAccountName response object
     */
    @Override
    public UserAccountResponse getUserAccountInfo(String userAccountName) {
       return userAccountClient.getUserAccountInfo(userAccountName);
    }

}
