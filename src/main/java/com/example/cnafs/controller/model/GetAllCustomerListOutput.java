package com.example.cnafs.controller.model;

import com.example.cnafs.controller.model.dto.CustomerDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetAllCustomerListOutput {
    List<CustomerDto> customers;
}
