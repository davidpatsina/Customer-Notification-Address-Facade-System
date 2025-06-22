package com.example.cnafs.controller.model;

import com.example.cnafs.controller.model.dto.AddressDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetAddressesByCustomerIdOutput {
    String customerId;

    List<AddressDto> addresses;
}
