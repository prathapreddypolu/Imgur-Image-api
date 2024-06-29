package com.ecom.imgur.repository;

import com.ecom.imgur.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        userRepository.save(user);

        Optional<User> actualUser = userRepository.findByUsername(user.getUsername());

        assertTrue(actualUser.isPresent());
        assertEquals(user, actualUser.get());
    }

    @Test
    void testFindByUsernameReturnsEmpty() {
        Optional<User> actualUser = userRepository.findByUsername("nonExistentUser");
        assertTrue(actualUser.isEmpty());
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setUsername("testUser1");
        user1.setPassword("testPassword1");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("testUser2");
        user2.setPassword("testPassword2");
        userRepository.save(user2);

        List<User> actualUsers = userRepository.findAll();

        assertEquals(2, actualUsers.size());
        assertTrue(actualUsers.contains(user1));
        assertTrue(actualUsers.contains(user2));
    }

    @Test
    void testSaveUserDetails() {
        User newUser = new User();
        newUser.setUsername("newUser");
        newUser.setPassword("newPassword");

        assertNull(newUser.getId());

        User savedUser = userRepository.save(newUser);

        assertNotNull(savedUser.getId());
        assertEquals(newUser.getUsername(), savedUser.getUsername());
        assertEquals(newUser.getPassword(), savedUser.getPassword());

        Optional<User> actualUser = userRepository.findById(savedUser.getId());
        assertTrue(actualUser.isPresent());
        assertEquals(savedUser, actualUser.get());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setUsername("testUser1");
        user.setPassword("testPassword1");
        userRepository.save(user);
        userRepository.delete(user);
        Optional<User> actualUser = userRepository.findById(user.getId());
        assertTrue(actualUser.isEmpty());
    }
}