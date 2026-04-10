package com.ecommerce.notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Notification")
public class Notification {

    @Id
    private String id;

    private Long userId;
    private String type; // EMAIL, SMS, PUSH
    private String subject;
    private String message;
    private String recipient;
    private String status = "PENDING"; // PENDING, SENT, FAILED
    private LocalDateTime sentAt;
    private LocalDateTime createdAt;

    public Notification(String id, Long userId, String type, String subject, String message, String recipient) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.subject = subject;
        this.message = message;
        this.recipient = recipient;
        this.status = "PENDING";
        this.createdAt = LocalDateTime.now();
    }
}
