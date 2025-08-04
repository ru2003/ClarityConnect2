package com.clarityconnect.backend.controller;

import com.clarityconnect.backend.model.User;
import com.clarityconnect.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.clarityconnect.backend.dto.ForgotPasswordRequest;
import com.clarityconnect.backend.dto.ResetPasswordRequest;
import com.clarityconnect.backend.service.OtpService;

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
        if (validUser != null) {
            return "Login successful. Role: " + validUser.getRole();
        } else {
            return "Invalid credentials";
        }
    }


    @Autowired private OtpService otpService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        otpService.generateOtp(request.getEmail());
        return ResponseEntity.ok("OTP sent to email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        otpService.resetPassword(request.getEmail(), request.getOtp(), request.getNewPassword());
        return ResponseEntity.ok("Password successfully reset");
    }

}
