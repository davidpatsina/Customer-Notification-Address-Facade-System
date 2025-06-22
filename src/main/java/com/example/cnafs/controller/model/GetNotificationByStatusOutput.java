package com.example.cnafs.controller.model;

import com.example.cnafs.controller.model.dto.NotificationDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetNotificationByStatusOutput {
    List<NotificationDto> notifications;
}
