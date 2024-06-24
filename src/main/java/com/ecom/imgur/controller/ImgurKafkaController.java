package com.ecom.imgur.controller;

import com.ecom.imgur.model.Image;
import com.ecom.imgur.model.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Publishing  User and Image details in Kafka topic
 */
@RestController
@Slf4j
@RequestMapping(path="/api/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImgurKafkaController {

    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper mapper = new ObjectMapper();
    /**
     *
     * @param userInfo
     * @return Response message
     */
    @PostMapping("user/publish")
    public ResponseEntity<Object> publishUserInfo(@RequestBody UserInfo userInfo) {
        try {
            log.info( " UserInfo Request Object {}",userInfo);
            kafkaTemplate.send("my-topic", mapper.writeValueAsString(userInfo));
            log.debug( "User details are successfully published in Kafka "+userInfo);
        } catch (JsonProcessingException e) {}
        return ResponseEntity.ok("User details are successfully published in Kafka");
    }
    /**
     *
     * @param image
     * @return Response message
     */
    @PostMapping("image/publish")
    public ResponseEntity<Object> publishImageInfo(@RequestBody Image image) {
        log.info( "Image Request Object {}",image);
        try {
            kafkaTemplate.send("my-topic", mapper.writeValueAsString(image));
            log.debug( "Image details are successfully published in Kafka {}",image);
        } catch (JsonProcessingException e) {}
        return ResponseEntity.ok("Image details are successfully published in Kafka");
    }
}
