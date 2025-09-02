package com.bfpc.service.impl;

import com.bfpc.dto.NotificationDto;
import com.bfpc.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final List<NotificationDto> notifications = initializeNotifications();

    @Override
    public List<NotificationDto> getUserNotifications(String userId) {
        return notifications.stream()
                .filter(n -> n.getUserId().equals(userId))
                .toList();
    }

    @Override
    public void markAsRead(String notificationId) {
        notifications.stream()
                .filter(n -> n.getId().equals(notificationId))
                .findFirst()
                .ifPresent(notification -> notification.setRead(true));
    }

    @Override
    public void markAllAsRead(String userId) {
        notifications.stream()
                .filter(n -> n.getUserId().equals(userId))
                .forEach(notification -> notification.setRead(true));
    }

    @Override
    public void deleteNotification(String notificationId) {
        notifications.removeIf(n -> n.getId().equals(notificationId));
    }

    @Override
    public NotificationDto createNotification(NotificationDto notificationDto) {
        notificationDto.setId(UUID.randomUUID().toString());
        notificationDto.setTimestamp(LocalDateTime.now());
        notificationDto.setRead(false);
        notifications.add(notificationDto);
        return notificationDto;
    }

    private List<NotificationDto> initializeNotifications() {
        List<NotificationDto> initialNotifications = new ArrayList<>();
        
        initialNotifications.add(new NotificationDto(
                "notif-1", "current-user", "New Training Available",
                "Modern Rice Farming Techniques training is now open for registration",
                "info", false, LocalDateTime.now().minusHours(2),
                "/events/event-1", "📚"
        ));
        
        initialNotifications.add(new NotificationDto(
                "notif-2", "current-user", "Market Price Alert",
                "Rice prices have increased by 15% in Makurdi market",
                "success", false, LocalDateTime.now().minusHours(4),
                "/dashboard", "📈"
        ));
        
        return initialNotifications;
    }
}