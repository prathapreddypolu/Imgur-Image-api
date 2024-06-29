package com.ecom.imgur.service;

import com.ecom.imgur.exception.UserException;
import com.ecom.imgur.exception.UserNotFoundException;
import com.ecom.imgur.model.User;
import com.ecom.imgur.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UserServiceImpl.class })
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Test
    void testRegisterUser() {
        // Arrange
        User user = getUser("test-username","test-password");
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUsername("test-username");
        expectedUser.setPassword("test-password");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(expectedUser);

        // Act
        User actualUser = userService.registerUser(user);

        // Assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testRegisterUserWithUserNamePresent() {
        User user = getUser("username","password");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        assertThrows(UserException.class, () -> userService.registerUser(user));
    }

    @Test
    void testGetUserByUsername() {
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
    void testGetUserByUsernameNotFound() {
        String username = "username";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername(username));
    }

    @Test
    void testAuthenticateUserWhenTrue() {
        String username = "test-username";
        String password = "test-password";
        User user = getUser(username,password);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        assertTrue(userService.authenticateUser(username, password));
    }

    @Test
    void testAuthenticateUserWhenFalse() {
        // Arrange
        String username = "username";
        String password = "password";
        User user = new User();
        user.setUsername("newUsername");
        user.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        assertFalse(userService.authenticateUser(username, password));
    }

    @Test
    void testAuthenticateUserWhenUserNotFound() {
        String username = "username";
        String password = "password";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.authenticateUser(username, password));
    }

    private User getUser(String userName,String password){
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        return user;
    }

}