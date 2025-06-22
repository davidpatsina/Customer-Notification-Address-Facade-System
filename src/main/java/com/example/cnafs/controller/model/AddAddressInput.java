package com.example.cnafs.controller.model;

import com.example.cnafs.controller.model.dto.AddressDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddAddressInput {
    @NotBlank(message = "CustomerId is mandatory")
    String customerId;

    AddressDto address;
}
