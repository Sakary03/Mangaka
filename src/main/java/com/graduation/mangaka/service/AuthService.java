package com.graduation.mangaka.service;

import com.graduation.mangaka.dto.request.ForgotPasswordRequest;
import com.graduation.mangaka.dto.request.LoginRequest;
import com.graduation.mangaka.dto.request.SignupRequest;
import com.graduation.mangaka.model.TypeAndRole.UserRole;
import com.graduation.mangaka.model.User;
import com.graduation.mangaka.repository.UserRepository;
import com.graduation.mangaka.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        User userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional!=null && passwordEncoder.matches(loginRequest.getPassword(), userOptional.getPassword())) {
            String token = jwtUtil.generateToken(userOptional.getEmail());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
    public ResponseEntity<?> signup(SignupRequest signupRequest) {
        if (userRepository.findByUserName(signupRequest.getUsername())!=null) {
            return ResponseEntity.status(409).body("Username already exists");
        }
        if (userRepository.findByEmail(signupRequest.getEmail())!=null) {
            return ResponseEntity.status(409).body("Email already exists");
        }
        User user = new User();
        user.setUserName(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setRole(UserRole.USER);
        userRepository.save(user);

        return ResponseEntity.status(201).body("User registered successfully");
    }

    public ResponseEntity<?> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        User userOptional = userRepository.findByEmail(forgotPasswordRequest.getEmail());
        if (userOptional!=null) {
            // Logic to handle forgot password, e.g., sending an email with reset instructions
            return ResponseEntity.ok("Password reset instructions sent to your email");
        } else {
            return ResponseEntity.status(404).body("Email not found");
        }
    }
}