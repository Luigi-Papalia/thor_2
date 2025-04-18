package com.vulnerableapp.vulnerable_java_app.controller;

import com.vulnerableapp.vulnerable_java_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/find")
    public String findUserByName(@RequestParam String name) {
        return userService.findUserByName(name);
    }

    @GetMapping("/login")
    public String doLogin(@RequestParam String name, @RequestParam String password) {
        return userService.doLogin(name, password);
    }
}
