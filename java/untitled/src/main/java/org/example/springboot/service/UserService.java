package org.example.springboot.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String login(String username, String password) {
        return String.format(" %s login with password: %s\n", username, password);
    }
}
