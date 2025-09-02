package com.bfpc.service;

import com.bfpc.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> getUserNotifications(String userId);
    void markAsRead(String notificationId);
    void markAllAsRead(String userId);
    void deleteNotification(String notificationId);
    NotificationDto createNotification(NotificationDto notificationDto);
}