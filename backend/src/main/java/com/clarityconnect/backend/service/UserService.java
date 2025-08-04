package com.clarityconnect.backend.service;

import com.clarityconnect.backend.model.User;
import com.clarityconnect.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        // Optional: Check if role is provided, else default to CLIENT
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("CLIENT");
        }

        // You should hash the password here in real projects
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password)) // In real life, use hashed password check
                .orElse(null);
    }
}
