package com.ecommerce.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private String id;
    private Long userId;
    private String type;
    private String subject;
    private String message;
    private String recipient;
    private String status;
    private LocalDateTime sentAt;
    private LocalDateTime createdAt;
}
