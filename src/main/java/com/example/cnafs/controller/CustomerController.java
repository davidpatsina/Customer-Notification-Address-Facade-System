package com.example.cnafs.controller;

import com.example.cnafs.controller.model.CreateCustomerInput;
import com.example.cnafs.service.CustomerService;
import com.example.cnafs.service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<String> createCustomer(Authentication authentication, @Validated @RequestBody CreateCustomerInput input) {
        String adminId = authentication.getPrincipal().toString();

        List<Address> addresses = input.getAddresses().stream()
                .map(addressDto -> {
                    Address address = Address.builder()
                            .value(addressDto.getValue())
                            .addressType(AddressType.valueOf(addressDto.getAddressType()))
                            .build();

                    return address;
                }).toList();

        List<NotificationPreference> preferences = input.getPreferences().stream()
                .map(notificationPreferenceDto -> {
                    NotificationPreference notificationPreference = NotificationPreference.builder()
                            .notificationPreferenceType(NotificationPreferenceType.valueOf(notificationPreferenceDto.getNotificationPreferenceType()))
                            .isOptedIn(notificationPreferenceDto.getIsOpted())
                            .build();
                    return notificationPreference;
                }).toList();

        Customer customer = Customer.builder()
                .name(input.getName())
                .addresses(addresses)
                .notificationPreferences(preferences)
                .build();

        return ResponseEntity.ok(customerService.createCustomer(adminId, customer));
    }
 }
