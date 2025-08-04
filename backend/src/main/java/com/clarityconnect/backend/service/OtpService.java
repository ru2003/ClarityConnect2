package com.clarityconnect.backend.service;

import com.clarityconnect.backend.model.User;
import com.clarityconnect.backend.repository.UserRepository;
import com.clarityconnect.backend.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;


@Service
public class OtpService {
    @Autowired private UserRepository userRepository;
    @Autowired private EmailService emailService;

    public void generateOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5)); // expires in 5 mins

        userRepository.save(user);

        emailService.sendOtpEmail(email, otp);
    }


    public void resetPassword(String email, String otp, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        if (user.getOtp() == null || user.getOtpExpiry().isBefore(LocalDateTime.now()) || !user.getOtp().equals(otp)) {
            throw new IllegalArgumentException("Invalid or expired OTP");
        }

        if (!isStrongPassword(newPassword)) {
            throw new IllegalArgumentException("Password must be at least 8 characters, contain upper/lowercase, digit, and symbol");
        }

        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        user.setOtp(null);
        user.setOtpExpiry(null);
        userRepository.save(user);
    }

    private boolean isStrongPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$");
    }
}
