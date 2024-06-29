package com.ecom.imgur.service;

import com.ecom.imgur.client.ImagesClient;
import com.ecom.imgur.client.UserAccountClient;
import com.ecom.imgur.model.ImageResponse;
import com.ecom.imgur.model.ImagesResponse;
import com.ecom.imgur.model.UserAccountResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { ImageServiceImpl.class })
public class ImageServiceImplTest {

    @MockBean
    private ImagesClient imagesClient;
    @MockBean
    private UserAccountClient userAccountClient;
    @Mock
    private MultipartFile mockFile;

    @Autowired
    private ImageServiceImpl imageService;

    @Test
    void testGetImages() {
        // Arrange
        String username = "username";
        ImagesResponse expectedResponse = new ImagesResponse();
        when(imagesClient.getImageDetails(username)).thenReturn(expectedResponse);
        ImagesResponse actualResponse = imageService.getImages(username);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testSaveImage() {
        // Arrange
        ImageResponse expectedResponse = new ImageResponse();
        when(imagesClient.uploadImage(mockFile)).thenReturn(expectedResponse);
        ImageResponse actualResponse = imageService.saveImage( mockFile);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testDeleteImage() throws Exception {
        // Arrange
        String imageId = "image-id";
        imageService.deleteImage(imageId);
        verify(imagesClient, times(1)).deleteImage(imageId);
    }

    @Test
    void testGetUserAccountInfo() throws Exception {
        String userAccountName = "userAccountName";
        UserAccountResponse expectedResponse = new UserAccountResponse();
        when(userAccountClient.getUserAccountInfo(userAccountName)).thenReturn(expectedResponse);
        UserAccountResponse actualResponse = imageService.getUserAccountInfo(userAccountName);
        assertEquals(expectedResponse, actualResponse);
    }
}