package com.example.cnafs.service.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
    String id;

    String name;

    List<Address> addresses;

    List<NotificationPreference> notificationPreferences;
}
