package com.example.cnafs.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateNewNotificationPreferenceInput {
    @NotBlank(message = "NotificationPreferenceId is mandatory")
    String notificationPreferenceId;

    String notificationPreferenceType;

    Boolean isOpted;
}