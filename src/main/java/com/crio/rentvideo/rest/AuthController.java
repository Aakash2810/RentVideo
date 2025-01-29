package com.crio.rentvideo.rest;



import com.crio.rentvideo.entity.AppUser;
import com.crio.rentvideo.repository.UserRepository;
import com.crio.rentvideo.service.impl.UserServiceImpl;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl customUserDetailsService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(AppUser.Role.CUSTOMER);  // Default to CUSTOMER
        }
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    // Login (Basic Auth)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AppUser user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        return ResponseEntity.ok("Login successful");
    }
}