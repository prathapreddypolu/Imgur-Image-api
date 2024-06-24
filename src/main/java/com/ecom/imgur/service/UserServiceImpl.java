package com.ecom.imgur.service;

import com.ecom.imgur.model.User;
import com.ecom.imgur.repository.UserRepository;
import com.ecom.imgur.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public
    User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            //throw new UsernameException("Username already exists");
        }
       // String hashedPassword = passwordEncoder.encode(user.getPassword());

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());

        //userRepository.save(newUser);
        return userRepository.save(newUser);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
