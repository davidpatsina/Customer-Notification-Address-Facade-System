package com.example.cnafs.service;

import com.example.cnafs.service.model.NotificationPreference;

import java.util.List;

public interface NotificationPreferenceService {
    void updateNotificationPreference(String adminId, NotificationPreference notificationPreference);
    String addNotificationPreference(String adminId, String customerId, NotificationPreference notificationPreference);
    List<NotificationPreference> getNotificationPreferencesByCustomerId(String adminId, String customerId);
}
