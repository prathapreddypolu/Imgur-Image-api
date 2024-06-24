package com.ecom.imgur.controller;

import com.ecom.imgur.model.UserAccountResponse;
import com.ecom.imgur.service.ImageServiceImpl;
import com.ecom.imgur.model.ImageResponse;
import com.ecom.imgur.model.ImagesResponse;
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

    @GetMapping("/view/{username}")
    public ResponseEntity<ImagesResponse> viewImage(@PathVariable String username) {
        ImagesResponse images=imageService.getImages(username);
        return ResponseEntity.ok().body(images);
    }

    @PostMapping("/upload/{username}")
    public ResponseEntity<ImageResponse> uploadImage(@PathVariable String username, @RequestParam("file") MultipartFile file) {

        ImageResponse imgurApiResponse= imageService.saveImage(username,file);
        return ResponseEntity.ok(imgurApiResponse);
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<String> deleteImageForAuthenticatedUser(@PathVariable String imageId) {
        imageService.deleteImage(imageId);
        log.info("Image deleted successfully for user: {}", imageId);
        return ResponseEntity.ok("Image deleted successfully");
    }

    @GetMapping("/user/{userAccountName}")
    public ResponseEntity<UserAccountResponse> userAccountInfo(@PathVariable String userAccountName) {
        UserAccountResponse userAccountInfo = imageService.getUserAccountInfo(userAccountName);
        log.info(" User Account Response details : id {} ,url {}, accountCreatedOn {}, AccountBlockedStatus{}", userAccountInfo.getUserDetails().getId(),
                userAccountInfo.getUserDetails().getUrl(), userAccountInfo.getUserDetails().getCreated(), userAccountInfo.getUserDetails().isBlocked());
        return ResponseEntity.ok().body(userAccountInfo);
    }

}
