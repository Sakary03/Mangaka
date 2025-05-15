package com.graduation.mangaka.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private String message;
    private Long userId;
    private String type;
    private LocalDateTime createdAt;
    private boolean isRead;
}
