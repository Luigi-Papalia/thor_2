package com.vulnerableapp.vulnerable_java_app.service;

import com.vulnerableapp.vulnerable_java_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    public String doLogin(String name, String password) {
        return userRepository.doLogin(name, password);
    }
}
