/*
package com.ecom.imgur.service;


import com.ecom.imgur.exception.UserNotFoundException;
import com.ecom.imgur.model.User;
import com.ecom.imgur.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = { ImageServiceImpl.class })
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testRegisterUser() throws Exception {
        // Arrange
        User user = new User();
        user.setUsername("fake-username");
        user.setPassword("fake-password");
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUsername("fake-username");
        expectedUser.setPassword("fake-password");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(expectedUser);

        // Act
        User actualUser = userService.registerUser(user);

        // Assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testRegisterUserDuplicateUsername() throws Exception {
        User user = new User();
        user.setUsername("fake-username");
        user.setPassword("fake-password");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

       // assertThrows(I.class, () -> userService.registerUser(user));
    }

    @Test
    public void testGetUserByUsername() throws Exception {
        // Arrange
        String username = "username";
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUsername(username);
        expectedUser.setPassword("password");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.getUserByUsername(username);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testGetUserByUsernameNotFound() throws Exception {
        String username = "fake-username";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername(username));
    }
}*/
