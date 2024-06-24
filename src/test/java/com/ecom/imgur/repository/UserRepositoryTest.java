/*
package com.ecom.imgur.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ecom.imgur.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;


    @Test
    public void testFindByUsername() {
        // Arrange
        String username = "username";
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUsername(username);
        expectedUser.setPassword("password");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));
        Optional<User> actualUser = userRepository.findByUsername(username);

        assertTrue(actualUser.isPresent());
        assertEquals(expectedUser, actualUser.get());
    }

    @Test
    public void testFindByUsernameNotFound() {

        String username = "username";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        Optional<User> actualUser = userRepository.findByUsername(username);
        assertFalse(actualUser.isPresent());
    }
}*/
