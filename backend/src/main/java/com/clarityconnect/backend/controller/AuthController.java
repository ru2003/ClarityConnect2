package com.clarityconnect.backend.controller;

import com.clarityconnect.backend.model.User;
import com.clarityconnect.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User validUser = userService.login(user.getEmail(), user.getPassword());
        return (validUser != null) ? "Login successful" : "Invalid credentials";
    }
}
