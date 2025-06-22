package com.example.cnafs.controller.model.dto;

import com.example.cnafs.repository.model.AddressEntity;
import com.example.cnafs.repository.model.NotificationStatusEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationDto {
    private String id;

    private String notificationStatusType;


    @Column(name = "text", nullable = false)
    private String text;
}