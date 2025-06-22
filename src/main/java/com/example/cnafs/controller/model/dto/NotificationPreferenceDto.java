package com.example.cnafs.controller.model.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationPreferenceDto {
    String id;

    @Pattern(regexp = "EMAIL|SMS|POSTAL", message = "Type must be either EMAIL, SMS or POSTAL")
    String notificationPreferenceType;

    Boolean isOpted;
}
