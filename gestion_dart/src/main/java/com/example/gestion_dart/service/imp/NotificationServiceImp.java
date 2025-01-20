package com.example.gestion_dart.service.imp;

import com.example.gestion_dart.entity.Notification;
import com.example.gestion_dart.repository.NotificationRepository;
import com.example.gestion_dart.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImp implements NotificationService {
    private NotificationRepository notificationRepository;

    @Autowired
    public void NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationServiceImp(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
    @Override
    public void deleteNotificationById(Long notificationId) {
        notificationRepository.deleteById(notificationId);}
}
