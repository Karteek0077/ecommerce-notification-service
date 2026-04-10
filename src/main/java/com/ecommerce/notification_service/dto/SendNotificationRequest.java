package com.ecommerce.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationRequest {
    private Long userId;
    private String type; // EMAIL, SMS, PUSH
    private String subject;
    private String message;
    private String recipient;
}
