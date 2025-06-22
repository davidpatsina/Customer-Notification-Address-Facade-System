package com.example.cnafs.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCustomerNameInput {

    @NotBlank(message = "CustomerId is mandatory")
    String customerId;

    @Size(min = 4, message = "Name must be at least 4 characters")
    String customerName;
}
