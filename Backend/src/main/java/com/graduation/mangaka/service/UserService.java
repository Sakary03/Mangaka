package com.graduation.mangaka.service;

import com.graduation.mangaka.dto.request.SignupRequest;
import com.graduation.mangaka.model.TypeAndRole.UserRole;
import com.graduation.mangaka.model.User;
import com.graduation.mangaka.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    public User registerUser(SignupRequest registerUserDTO) throws MessagingException {
        User newUser=new User();
        newUser.setUserName(registerUserDTO.getUsername());
        newUser.setFullName(registerUserDTO.getName());
        newUser.setEmail(registerUserDTO.getEmail());
        newUser.setDob(registerUserDTO.getDate());
        newUser.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        if (registerUserDTO.getRole().equals("USER")) newUser.setRole(UserRole.USER);
        else newUser.setRole(UserRole.ADMIN);
        newUser.setAvatarUrl(registerUserDTO.getAvatar());
        emailService.registerCompleteEmail(newUser);
        return userRepository.save(newUser);
    }
}