package com.ecom.imgur.service;

import com.ecom.imgur.exception.UserException;
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

    /**
     *
     * @param user
     * @return User response object
     */
    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserException("Username already exists");
        }
        return userRepository.save(user);
    }
    /**
     *
     * @param username
     * @return User response object
     */
    @Override
    public User getUserByUsername(String username) {
       return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    /**
     *
     * @param userName
     * @param password
     * @return return user validation flag
     */
    public boolean authenticateUser(String userName, String password)
    {
      User user= userRepository.findByUsername(userName)
               .orElseThrow(() -> new UserNotFoundException("User not found"));
      return userName.equals(user.getUsername()) && password.equals(user.getPassword());
    }
}
