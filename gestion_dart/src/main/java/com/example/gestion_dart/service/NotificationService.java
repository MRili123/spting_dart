package com.example.gestion_dart.service;

import com.example.gestion_dart.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getNotificationsByUserId(Long userId);
    void deleteNotificationById(Long notificationId);
}
