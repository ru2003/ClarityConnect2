package com.clarityconnect.backend.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_expiry")
    private LocalDateTime otpExpiry;


    // Getters & Setters
}
