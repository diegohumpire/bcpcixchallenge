package com.app.bcpcixchallenge.security.services;

import com.app.bcpcixchallenge.security.User;

public interface UserService {
    User saveUser(User user);
    User getUser(String username);
}
