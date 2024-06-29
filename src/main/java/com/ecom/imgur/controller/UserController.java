package com.ecom.imgur.controller;

import com.ecom.imgur.model.User;
import com.ecom.imgur.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path="/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {


    @Autowired
    private UserServiceImpl userService;
    /**
     *
     * @param user User
     * @return User response Object
     */
    @GetMapping("/registration")
    public
    ResponseEntity<User> registerUser(@RequestBody User user) {

        User registeredUser = userService.registerUser(user);
        log.info("Registered user successfully: {}", user.getUsername());
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
    /**
     *
     * @param username User Name
     * @return User response Object
     */
   @GetMapping("/{username}")
    public ResponseEntity<User> getUserByName(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        log.info("User details Retrieved successfully for user: {}", username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
