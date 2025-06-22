package com.example.cnafs.controller.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDto {
    String id;
    String name;
    List<AddressDto> addressDtos;
    List<NotificationPreferenceDto> notificationPreferenceDtos;
}
