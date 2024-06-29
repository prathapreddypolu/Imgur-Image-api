
package com.ecom.imgur.controller;

import com.ecom.imgur.model.ImageResponse;
import com.ecom.imgur.model.ImagesResponse;
import com.ecom.imgur.model.UserAccount;
import com.ecom.imgur.model.UserAccountResponse;
import com.ecom.imgur.service.ImageServiceImpl;
import com.ecom.imgur.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ImageControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private ImageServiceImpl imageService;

    @MockBean private UserServiceImpl userService;
    @Mock
    UserAccountResponse userAccountResponse;

    @Mock
    UserAccount userAccount;

    private MockMultipartFile mockMultipartFile;

    @BeforeEach
    void setUp() {
        mockMultipartFile = new MockMultipartFile("file", "test-image.jpg", MediaType.IMAGE_JPEG_VALUE, "test-image".getBytes());
    }

    @Test
    void viewImage_shouldReturnListOfImages() throws Exception {

        Mockito.when(userService.authenticateUser(anyString(), anyString())).thenReturn(true);
        ImagesResponse imagesResponse = new ImagesResponse();
        Mockito.when(imageService.getImages(anyString())).thenReturn(imagesResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/images/view/user123")
                .header("userID", "imagur")
                .header("password", "imagur")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void uploadImage_shouldReturnImageResponse() throws Exception {

        Mockito.when(userService.authenticateUser(anyString(), anyString())).thenReturn(true);
        ImageResponse imageResponse = new ImageResponse();
        when(imageService.saveImage(any(MockMultipartFile.class))).thenReturn(imageResponse);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/images/upload/")
                .file(mockMultipartFile)
                .header("userID", "imagur")
                .header("password", "imagur")
                .contentType(MediaType.MULTIPART_FORM_DATA);

        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
    }

   @Test
    void deleteImageForAuthenticatedUser_shouldDeleteImage() throws Exception {
       Mockito.when(userService.authenticateUser(anyString(), anyString())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/images/delete/123")
                .header("userID", "imagur")
                .header("password", "imagur")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void userAccountInfo_shouldReturnUserAccountResponse() throws Exception {
        Mockito.when(imageService.getUserAccountInfo(anyString())).thenReturn(userAccountResponse);
        when(userAccountResponse.getUserDetails()).thenReturn(userAccount);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/images/user/test-user-account").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void viewImage_shouldThrowUserException_whenUserAuthenticationFails() throws Exception {
        Mockito.when(userService.authenticateUser(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/images/view/user123")
                        .header("userID", "test-id")
                        .header("password", "test-password")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User is already Registered"));
    }

    @Test
    void uploadImage_shouldThrowUserException_whenUserAuthenticationFails() throws Exception {
        Mockito.when(userService.authenticateUser(anyString(), anyString())).thenReturn(false);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/images/upload/")
                .file(mockMultipartFile)
                .header("userID", "test-id")
                .header("password", "test-password")
                .contentType(MediaType.MULTIPART_FORM_DATA);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User is already Registered"));
    }


    @Test
    void deleteImageForAuthenticatedUser_shouldThrowUserException_whenUserAuthenticationFails() throws Exception {
        Mockito.when(userService.authenticateUser(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/images/delete/imageID")
                        .header("userID", "test-id")
                        .header("password", "test-password")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User is already Registered"));

}
}