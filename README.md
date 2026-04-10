# Notification Service

Notification management service for the ecommerce platform.

## Port
- **8085**

## Description
Handles email, SMS, and push notifications using Redis pub/sub for real-time messaging.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/notifications | Send notification |
| GET | /api/notifications | Get all notifications |
| GET | /api/notifications/{id} | Get notification by ID |
| GET | /api/notifications/user/{userId} | Get notifications by user |
| GET | /api/notifications/type/{type} | Get notifications by type |
| PUT | /api/notifications/{id}/read | Mark notification as read |

## Running the Service

```bash
mvn spring-boot:run
```

## Dependencies
- Redis: localhost:6379
- Eureka Server: http://localhost:8761
