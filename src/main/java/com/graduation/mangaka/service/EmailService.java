package com.graduation.mangaka.service;

import com.graduation.mangaka.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sharkkary@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
        System.out.println("Email sent");
    }
    
    public void registerCompleteEmail(User user) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("sharkkary@gmail.com");
        helper.setTo(user.getEmail());
        helper.setSubject("Đăng ký tài khoản thành công");
        String content="<html>\n" +
                "                <body>\n" +
                "                    <h2>Welcome to Mangaka,"+user.getFullName() + "!</h2>\n" +
                "                    <p>Thank you for registering with us. We are excited to have you as part of our community.</p>\n" +
                "                    <p>Here are some details about your account:</p>\n" +
                "                    <ul>\n" +
                "                        <li><strong>Username:</strong>"+user.getUserName()+"</li>\n" +
                "                        <li><strong>Role:</strong> User</li>\n" +
                "                    </ul>\n" +
                "                    <p>We hope you enjoy your experience with our service. If you have any questions, feel free to reach out to our support team.</p>\n" +
                "                    <p>Best regards,</p>\n" +
                "                    <p><strong>The Mangaka Team</strong></p>\n" +
                "                </body>\n" +
                "                </html>";
        helper.setText(content, true);
        mailSender.send(message);
        System.out.println("Email sent");
    }
    
    public void sendPasswordResetEmail(User user, String resetToken) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("sharkkary@gmail.com");
        helper.setTo(user.getEmail());
        helper.setSubject("Mangaka - Password Reset Request");
        
        // Frontend URL with the reset token as a query parameter
        String resetUrl = "http://localhost:5173/reset-password?token=" + resetToken;
        
        String content = "<html>\n" +
                "                <body>\n" +
                "                    <h2>Hello, " + user.getFullName() + "!</h2>\n" +
                "                    <p>We received a request to reset your password. If you didn't make this request, you can ignore this email.</p>\n" +
                "                    <p>To reset your password, please click the button below:</p>\n" +
                "                    <p style=\"text-align: center;\">\n" +
                "                        <a href=\"" + resetUrl + "\" style=\"display: inline-block; background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; font-weight: bold;\">Reset Password</a>\n" +
                "                    </p>\n" +
                "                    <p>Or copy and paste the following link into your browser:</p>\n" +
                "                    <p>" + resetUrl + "</p>\n" +
                "                    <p>This link will expire in 30 minutes for security reasons.</p>\n" +
                "                    <p>Best regards,</p>\n" +
                "                    <p><strong>The Mangaka Team</strong></p>\n" +
                "                </body>\n" +
                "                </html>";
        
        helper.setText(content, true);
        mailSender.send(message);
        System.out.println("Password reset email sent to " + user.getEmail());
    }
}
