package com.example.cnafs.controller.model.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDto {
    String id;

    @Size(min = 3, message = "Address value must be at least 3 characters")
    String value;

    @Pattern(regexp = "EMAIL|SMS|POSTAL", message = "Type must be either EMAIL, SMS or POSTAL")
    String addressType;
}

