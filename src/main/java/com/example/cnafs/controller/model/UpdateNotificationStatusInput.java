package com.example.cnafs.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateNotificationStatusInput {
    @NotBlank(message = "NotificationId is mandatory")
    String notificationId;

    @Pattern(regexp = "PENDING|DELIVERED|FAILED", message = "Type must be either PENDING, DELIVERED or FAILED")
    String notificationStatus;
}
