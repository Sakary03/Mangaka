package com.graduation.mangaka.service;

import com.graduation.mangaka.dto.request.ChangePasswordRequestDTO;
import com.graduation.mangaka.dto.request.SignupRequest;
import com.graduation.mangaka.dto.request.UserRequestDTO;
import com.graduation.mangaka.model.TypeAndRole.UserRole;
import com.graduation.mangaka.model.User;
import com.graduation.mangaka.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    public User GetUserInfo(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> GetAllUserByBatch(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return userRepository.findAll(pageable).getContent();
    }


    public User UpdateUser(UserRequestDTO userRequestDTO, Long id) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }

        existingUser.setUserName(userRequestDTO.getUsername());
        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setFullName(userRequestDTO.getName());
        existingUser.setDob(userRequestDTO.getDate());
        existingUser.setAddress(userRequestDTO.getAddress());
        existingUser.setAvatarUrl(userRequestDTO.getAvatar());

        return userRepository.save(existingUser);
    }

    public boolean changePassword(Long userId, ChangePasswordRequestDTO request) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return false;
        }

        if (!request.getNewPassword().equals(request.getNewPassword())) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return true;
    }
    
    /**
     * Creates a password reset token for the user with the given email
     * @param email User's email address
     * @return true if the password reset email was sent, false otherwise
     */
    public boolean createPasswordResetTokenForUser(String email) throws MessagingException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        
        String token = UUID.randomUUID().toString();
        
        // Set token expiration time (30 minutes from now)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        
        user.setPasswordResetToken(token);
        user.setPasswordResetTokenExpiry(calendar.getTime());
        
        userRepository.save(user);
        
        // Send password reset email
        emailService.sendPasswordResetEmail(user, token);
        
        return true;
    }
    
    /**
     * Validates a password reset token and resets the user's password
     * @param token Reset token
     * @param newPassword New password
     * @return true if password was reset successfully, false otherwise
     */
    public boolean resetPassword(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token);
        
        if (user == null) {
            return false;
        }
        
        // Check if token is expired
        Date now = new Date();
        if (user.getPasswordResetTokenExpiry() == null || user.getPasswordResetTokenExpiry().before(now)) {
            return false;
        }
        
        // Update password and clear reset token
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);
        
        userRepository.save(user);
        
        return true;
    }
}
