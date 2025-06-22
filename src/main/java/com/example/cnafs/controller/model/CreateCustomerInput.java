package com.example.cnafs.controller.model;

import com.example.cnafs.controller.model.dto.AddressDto;
import com.example.cnafs.controller.model.dto.NotificationPreferenceDto;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCustomerInput {
    @Size(min = 3, message = "Name must be at least 3 characters")
    String name;

    List<AddressDto> addresses;

    List<NotificationPreferenceDto> preferences;
}
