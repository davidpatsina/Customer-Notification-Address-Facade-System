package com.example.cnafs.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAddressInput {
    @NotBlank(message = "AddressId is mandatory")
    String addressId;

    String value;

    String addressType;
}
