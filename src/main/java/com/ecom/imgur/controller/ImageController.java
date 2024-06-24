package com.ecom.imgur.controller;

import com.ecom.imgur.common.Constant;
import com.ecom.imgur.exception.UserException;
import com.ecom.imgur.exception.UserNotFoundException;
import com.ecom.imgur.model.UserAccountResponse;
import com.ecom.imgur.service.ImageServiceImpl;
import com.ecom.imgur.model.ImageResponse;
import com.ecom.imgur.model.ImagesResponse;
import com.ecom.imgur.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping(path="/api/images", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {

    @Autowired
    private ImageServiceImpl imageService;

    @Autowired
    private UserServiceImpl userService;
    /**
     *
     * @param username
     * @return List of Images response ImagesResponse Object
     */

    @GetMapping("/view/{username}")
    public ResponseEntity<ImagesResponse> viewImage(@PathVariable String username,@RequestHeader String userID,@RequestHeader String password) {

        if(userService.authenticateUser(userID ,password))
        {
            ImagesResponse images=imageService.getImages(username);
            log.info("Successfully list of Images sent "+images.getImages());
            return ResponseEntity.ok().body(images);
        }else {
            throw new UserException(Constant.USER_AUTH_FAILED);
        }

    }
    /**
     *
     * @return Uploaded image ImageResponse object
     */
    @PostMapping("/upload/")
    public ResponseEntity<ImageResponse> uploadImage( @RequestParam("file") MultipartFile file,@RequestHeader String userID,@RequestHeader String password) {
        log.info("Upload image file name {} ",file.getName());
        if(userService.authenticateUser(userID ,password)) {
            ImageResponse imgurApiResponse = imageService.saveImage(file);
            log.info("Successfully image Upload  {} for ", file.getName());
            return ResponseEntity.ok(imgurApiResponse);
        }else {
            throw new UserException(Constant.USER_AUTH_FAILED);
        }
    }

    /**
     *
     * @param imageId
     * @return deleted imageId
     */
    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<String> deleteImageForAuthenticatedUser(@PathVariable String imageId,@RequestHeader String userID,@RequestHeader String password) {
        if(userService.authenticateUser(userID ,password)) {
        imageService.deleteImage(imageId);
        log.info("{} Image Successfully deleted", imageId);
        return ResponseEntity.ok(imageId+ "Image Successfully deleted");
        }else {
            throw new UserException(Constant.USER_AUTH_FAILED);
        }
    }
    /**
     *
     * @param userAccountName
     * @return UserAccountResponse
     */
    @GetMapping("/user/{userAccountName}")
    public ResponseEntity<UserAccountResponse> userAccountInfo(@PathVariable String userAccountName) {
        UserAccountResponse userAccountInfo = imageService.getUserAccountInfo(userAccountName);
        log.info(" User Account Response details : id {} ,url {}, accountCreatedOn {}, AccountBlockedStatus{}", userAccountInfo.getUserDetails().getId(),
                userAccountInfo.getUserDetails().getUrl(), userAccountInfo.getUserDetails().getCreated(), userAccountInfo.getUserDetails().isBlocked());
        return ResponseEntity.ok().body(userAccountInfo);
    }

}
