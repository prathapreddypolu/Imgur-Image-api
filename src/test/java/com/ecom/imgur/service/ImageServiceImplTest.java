/*
package com.ecom.imgur.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.ecom.imgur.client.ImagesClient;
import com.ecom.imgur.client.UserAccountClient;
import com.ecom.imgur.client.config.OAuth2Service;
import com.ecom.imgur.model.ImageResponse;
import com.ecom.imgur.model.ImagesResponse;
import com.ecom.imgur.model.UserAccountResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = { ImageServiceImpl.class })
public class ImageServiceImplTest {

    @Mock
    private ImagesClient imagesClient;
    @Mock
    private UserAccountClient userAccountClient;
    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    public void testGetImages() throws Exception {
        // Arrange
        String username = "username";
        ImagesResponse expectedResponse = new ImagesResponse();
        when(imagesClient.getImageDetails(username)).thenReturn(expectedResponse);
        ImagesResponse actualResponse = imageService.getImages(username);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testSaveImage() throws Exception {
        // Arrange
        String userName = "username";
        MultipartFile mockFile = mock(MultipartFile.class);
        ImageResponse expectedResponse = new ImageResponse();

        when(imagesClient.UploadImage(mockFile)).thenReturn(expectedResponse);
        ImageResponse actualResponse = imageService.saveImage(userName, mockFile);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testDeleteImage() throws Exception {
        // Arrange
        String imageId = "image-id";
        imageService.deleteImage(imageId);
        verify(imagesClient, times(1)).deleteImage(imageId);
    }

    @Test
    public void testGetUserAccountInfo() throws Exception {
        String userAccountName = "userAccountName";
        UserAccountResponse expectedResponse = new UserAccountResponse();
        when(userAccountClient.getUserAccountInfo(userAccountName)).thenReturn(expectedResponse);
        UserAccountResponse actualResponse = imageService.getUserAccountInfo(userAccountName);
        assertEquals(expectedResponse, actualResponse);
    }
}*/
