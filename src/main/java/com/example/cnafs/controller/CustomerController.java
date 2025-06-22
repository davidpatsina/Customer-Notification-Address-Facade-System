package com.example.cnafs.controller;

import com.example.cnafs.controller.model.CreateCustomerInput;
import com.example.cnafs.controller.model.GetAllCustomerListOutput;
import com.example.cnafs.controller.model.UpdateCustomerNameInput;
import com.example.cnafs.controller.model.dto.AddressDto;
import com.example.cnafs.controller.model.dto.CustomerDto;
import com.example.cnafs.controller.model.dto.NotificationPreferenceDto;
import com.example.cnafs.service.CustomerService;
import com.example.cnafs.service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @DeleteMapping("/delete_customer/{customerId}")
    public ResponseEntity<?> deleteCustomer(Authentication authentication, @PathVariable String customerId) {
        String adminId = authentication.getPrincipal().toString();
        customerService.deleteCustomer(adminId, customerId);
        return ResponseEntity.ok("Successfully deleted customer with id " + customerId);
    }

    @PutMapping
    public ResponseEntity<?> updateCustomerName(Authentication authentication, @RequestBody UpdateCustomerNameInput input) {
        String adminId = authentication.getPrincipal().toString();
        Customer customer = Customer.builder()
                .id(input.getCustomerId())
                .name(input.getCustomerName())
                .build();

        customerService.updateCustomerName(adminId, customer);
        return ResponseEntity.ok("Customer's name is successfully updated");
    }

    @GetMapping
    public ResponseEntity<?> getListCustomers(Authentication authentication) {
        String adminId = authentication.getPrincipal().toString();
        List<Customer> customers = customerService.getCustomers(adminId);

        List<CustomerDto> customerDtos = new ArrayList<>();

        for (Customer customer : customers) {
            List <NotificationPreferenceDto> notificationPreferenceDtots = new ArrayList<>();
            for (NotificationPreference notificationPreference : customer.getNotificationPreferences()){
                NotificationPreferenceDto notificationPreferenceDto = NotificationPreferenceDto.builder()
                        .id(notificationPreference.getId())
                        .isOpted(notificationPreference.getIsOptedIn())
                        .notificationPreferenceType(String.valueOf(notificationPreference.getNotificationPreferenceType()))
                        .build();
                notificationPreferenceDtots.add(notificationPreferenceDto);
            }

            List <AddressDto> addressDtos = new ArrayList<>();
            for (Address address : customer.getAddresses()){
                AddressDto addressDto = AddressDto.builder()
                        .addressType(String.valueOf(address.getAddressType()))
                        .value(address.getValue())
                        .build();
                addressDtos.add(addressDto);
            }

            CustomerDto customerDto = CustomerDto.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .notificationPreferenceDtos(notificationPreferenceDtots)
                    .addressDtos(addressDtos)
                    .build();
            customerDtos.add(customerDto);
        }

        GetAllCustomerListOutput output = GetAllCustomerListOutput.builder()
                .customers(customerDtos)
                .build();

        return ResponseEntity.ok(output);
    }
 }
