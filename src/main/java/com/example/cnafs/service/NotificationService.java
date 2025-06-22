package com.example.cnafs.service;

import com.example.cnafs.service.model.Notification;
import com.example.cnafs.service.model.NotificationStatus;

import java.util.List;

public interface NotificationService {
    String sendMessage(String adminId, String addressId, Notification notification);

    void updateMessage(String adminId, Notification notification);

    List<Notification> getNotificationsByStatus(String adminId, NotificationStatus status);
}
