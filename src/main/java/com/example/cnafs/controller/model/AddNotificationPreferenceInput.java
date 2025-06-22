package com.example.cnafs.controller.model;

import com.example.cnafs.controller.model.dto.NotificationPreferenceDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddNotificationPreferenceInput {
    @NotBlank(message = "CustomerId is mandatory")
    String customerId;

    NotificationPreferenceDto notificationPreference;
}
