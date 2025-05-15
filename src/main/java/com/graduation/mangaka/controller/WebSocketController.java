package com.graduation.mangaka.controller;

import com.graduation.mangaka.model.Notification;
import com.graduation.mangaka.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final NotificationService notificationService;

    @MessageMapping("/send-notification")
    public void sendNotification(@Payload Notification notification) {
        notificationService.createNotification(notification);
    }
}