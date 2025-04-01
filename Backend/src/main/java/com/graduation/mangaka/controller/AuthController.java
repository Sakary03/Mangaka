package com.graduation.mangaka.controller;

import com.graduation.mangaka.dto.request.LoginRequest;
import com.graduation.mangaka.dto.request.SignupRequest;
import com.graduation.mangaka.dto.response.LoginResponseDTO;
import com.graduation.mangaka.repository.UserRepository;
import com.graduation.mangaka.security.SecurityService;
import com.graduation.mangaka.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    SecurityService securityUtil;
    @Autowired
    UserService userService;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginDto) {
        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword());

        // xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // create a token
        String access_token = this.securityUtil.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setAccessToken(access_token);
        loginResponseDTO.setUserInfo(userRepository.findByUserName(loginDto.getUsername()));
        return ResponseEntity.ok().body(loginResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest newUser) throws MessagingException {
        boolean isEmailExist = userRepository.existsByEmail(newUser.getEmail());
        boolean isUsernameExist=userRepository.existsByUserName(newUser.getUsername());
        if (!isEmailExist && !isUsernameExist) {
            return ResponseEntity.ok().body(userService.registerUser(newUser));
        } else {
            return ResponseEntity.badRequest().body("Email hoặc tên người dùng đã tồn tại");
        }
    }
}