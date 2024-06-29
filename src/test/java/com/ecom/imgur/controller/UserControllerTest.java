package com.ecom.imgur.controller;

import com.ecom.imgur.model.User;
import com.ecom.imgur.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    void testRegisterUser() throws Exception {
        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");

        when(userService.registerUser(any(User.class))).thenReturn(testUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testUser\", \"password\": \"testPassword\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{\"username\": \"testUser\", \"password\": \"testPassword\"}"));
    }

    @Test
    void testGetUserByName() throws Exception {
        String testUsername = "testUser";
        User testUser = new User();
        testUser.setUsername(testUsername);
        testUser.setPassword("testPassword");

        when(userService.getUserByUsername(testUsername)).thenReturn(testUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/" + testUsername)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"username\": \"testUser\", \"password\": \"testPassword\"}"));
    }
}