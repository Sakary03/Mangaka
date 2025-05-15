package com.graduation.mangaka.service;

import com.graduation.mangaka.dto.request.NotificationDTO;
import com.graduation.mangaka.model.Notification;
import com.graduation.mangaka.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public List<Notification> getAllNotificationsForUser(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Notification> getUnreadNotificationsForUser(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
    }

    public long getUnreadNotificationCount(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    public Notification createNotification(Notification notification) {
        Notification savedNotification = notificationRepository.save(notification);

        // Send real-time notification through WebSocket
        messagingTemplate.convertAndSendToUser(
                String.valueOf(notification.getUserId()),
                "/queue/notifications",
                toDto(savedNotification)
        );

        return savedNotification;
    }

    public boolean markAsRead(Long notificationId) {
        Optional<Notification> notificationOpt = notificationRepository.findById(notificationId);
        if (notificationOpt.isPresent()) {
            Notification notification = notificationOpt.get();
            notification.setRead(true);
            notificationRepository.save(notification);
            return true;
        }
        return false;
    }

    public boolean markAllAsRead(Long userId) {
        List<Notification> unreadNotifications = notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
        unreadNotifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(unreadNotifications);
        return true;
    }

    // Helper method to convert to DTO
    private NotificationDTO toDto(Notification notification) {
        return new NotificationDTO(
                notification.getId(),
                notification.getMessage(),
                notification.getUserId(),
                notification.getType(),
                notification.getCreatedAt(),
                notification.isRead()
        );
    }
}