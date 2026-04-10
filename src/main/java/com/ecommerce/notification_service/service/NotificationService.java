package com.ecommerce.notification_service.service;

import com.ecommerce.notification_service.dto.NotificationDTO;
import com.ecommerce.notification_service.dto.SendNotificationRequest;
import com.ecommerce.notification_service.model.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RedisTemplate<String, Object> redisTemplate;

    public NotificationDTO sendNotification(SendNotificationRequest request) {
        String id = UUID.randomUUID().toString();
        Notification notification = new Notification(
                id,
                request.getUserId(),
                request.getType(),
                request.getSubject(),
                request.getMessage(),
                request.getRecipient()
        );

        // Save to Redis
        redisTemplate.opsForHash().put("notifications", id, notification);

        // Publish to Redis Pub/Sub for real-time processing
        redisTemplate.convertAndSend("notification-channel", notification);

        // Simulate sending
        notification.setStatus("SENT");
        notification.setSentAt(LocalDateTime.now());
        redisTemplate.opsForHash().put("notifications", id, notification);

        return mapToDTO(notification);
    }

    public List<NotificationDTO> getAllNotifications() {
        Set<Object> keys = redisTemplate.opsForHash().keys("notifications");
        if (keys == null || keys.isEmpty()) {
            return List.of();
        }

        return keys.stream()
                .map(key -> redisTemplate.opsForHash().get("notifications", key.toString()))
                .filter(Notification.class::isInstance)
                .map(n -> mapToDTO((Notification) n))
                .collect(Collectors.toList());
    }

    public NotificationDTO getNotificationById(String id) {
        Object notification = redisTemplate.opsForHash().get("notifications", id);
        if (notification == null) {
            throw new RuntimeException("Notification not found");
        }
        return mapToDTO((Notification) notification);
    }

    public List<NotificationDTO> getNotificationsByUserId(Long userId) {
        return getAllNotifications().stream()
                .filter(n -> userId.equals(n.getUserId()))
                .collect(Collectors.toList());
    }

    public List<NotificationDTO> getNotificationsByType(String type) {
        return getAllNotifications().stream()
                .filter(n -> type.equals(n.getType()))
                .collect(Collectors.toList());
    }

    public NotificationDTO markAsRead(String id) {
        Object notificationObj = redisTemplate.opsForHash().get("notifications", id);
        if (notificationObj == null) {
            throw new RuntimeException("Notification not found");
        }

        Notification notification = (Notification) notificationObj;
        notification.setStatus("READ");
        redisTemplate.opsForHash().put("notifications", id, notification);

        return mapToDTO(notification);
    }

    private NotificationDTO mapToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setUserId(notification.getUserId());
        dto.setType(notification.getType());
        dto.setSubject(notification.getSubject());
        dto.setMessage(notification.getMessage());
        dto.setRecipient(notification.getRecipient());
        dto.setStatus(notification.getStatus());
        dto.setSentAt(notification.getSentAt());
        dto.setCreatedAt(notification.getCreatedAt());
        return dto;
    }
}
