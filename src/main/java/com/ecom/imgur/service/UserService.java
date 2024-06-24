package com.ecom.imgur.service;

import com.ecom.imgur.model.User;

public interface UserService {

    User registerUser(User user);

    User getUserByUsername(String username);

}
