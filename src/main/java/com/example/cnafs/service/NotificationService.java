package com.example.cnafs.service;

import com.example.cnafs.service.model.Notification;

public interface NotificationService {
    String sendMessage(String adminId, String addressId, Notification notification);

    void updateMessage(String adminId, Notification notification);
}
