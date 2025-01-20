package com.example.gestion_dart.controller;

import com.example.gestion_dart.entity.Notification;
import com.example.gestion_dart.service.NotificationService;
import com.example.gestion_dart.service.imp.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @GetMapping("/deleteNotification/{notificationId}")
    public String deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotificationById(notificationId);
        return "redirect:/notificationAdmin";
    }
    @GetMapping("/deleteNotificationuser/{notificationId}")
    public String deleteNotificationuser(@PathVariable Long notificationId) {
        notificationService.deleteNotificationById(notificationId);
        return "redirect:/notificationUser";
    }
    @GetMapping("/notificationAdmin")
    public String notification(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof CustomUserDetails) {
            Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
            List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
            model.addAttribute("notifications", notifications);
        }

        return "admin/notification_admin";
    }
    @GetMapping("/notificationUser")
    public String notificationuser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof CustomUserDetails) {
            Long userId = ((CustomUserDetails) auth.getPrincipal()).getId();
            List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
            model.addAttribute("notifications", notifications);
        }

        return "user/notification_user";
    }
}
