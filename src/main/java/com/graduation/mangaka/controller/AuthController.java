package com.graduation.mangaka.controller;

import com.graduation.mangaka.dto.request.ChangePasswordRequestDTO;
import com.graduation.mangaka.dto.request.ForgotPasswordRequestDTO;
import com.graduation.mangaka.dto.request.LoginRequest;
import com.graduation.mangaka.dto.request.ResetPasswordRequestDTO;
import com.graduation.mangaka.dto.request.SignupRequest;
import com.graduation.mangaka.dto.response.LoginResponseDTO;
import com.graduation.mangaka.repository.UserRepository;
import com.graduation.mangaka.security.SecurityService;
import com.graduation.mangaka.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:5173")
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
                loginDto.getEmail(), loginDto.getPassword());

        // xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // create a token
        String access_token = this.securityUtil.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setAccessToken(access_token);
        loginResponseDTO.setUserInfo(userRepository.findByEmail(loginDto.getEmail()));
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

    @PutMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequestDTO requestDTO) {
        return ResponseEntity.ok().body(userService.changePassword(id, requestDTO));
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO requestDTO) {
        try {
            boolean result = userService.createPasswordResetTokenForUser(requestDTO.getEmail());
            
            if (result) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Password reset instructions have been sent to your email");
                return ResponseEntity.ok().body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
            }
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending password reset email: " + e.getMessage());
        }
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO requestDTO) {
        boolean result = userService.resetPassword(requestDTO.getToken(), requestDTO.getNewPassword());
        
        if (result) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Password has been reset successfully");
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid or expired token");
        }
    }
}