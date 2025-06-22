package com.example.cnafs.controller;

import com.example.cnafs.controller.model.AddAddressInput;
import com.example.cnafs.controller.model.GetAddressesByCustomerIdOutput;
import com.example.cnafs.controller.model.UpdateAddressInput;
import com.example.cnafs.controller.model.dto.AddressDto;
import com.example.cnafs.service.AddressService;
import com.example.cnafs.service.model.Address;
import com.example.cnafs.service.model.AddressType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<String> addAddress(Authentication authentication, @Validated @RequestBody AddAddressInput input){
        String adminId = authentication.getPrincipal().toString();
        String customerId = input.getCustomerId();
        Address address = Address.builder()
                .value(input.getAddress().getValue())
                .addressType(AddressType.valueOf(input.getAddress().getAddressType()))
                .build();

        return ResponseEntity.ok(addressService.addAddress(adminId, customerId, address));
    }

    @PutMapping
    public ResponseEntity<?> updateAddress(Authentication authentication, @Validated @RequestBody UpdateAddressInput input){
        String adminId = authentication.getPrincipal().toString();
        Address address = Address.builder()
                .id(input.getAddressId())
                .value(input.getValue())
                .addressType(AddressType.valueOf(input.getAddressType()))
                .build();

        addressService.updateAddress(adminId, address);
        return ResponseEntity.ok("Successfully updated address");
    }

    @DeleteMapping("/delete_address/{addressId}")
    public ResponseEntity<?> deleteAddress(Authentication authentication, @PathVariable String addressId) {
        String adminId = authentication.getPrincipal().toString();

        addressService.deleteAddress(adminId, addressId);

        return ResponseEntity.ok("Successfully deleted address");
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<GetAddressesByCustomerIdOutput> getAddressesByCustomerId(Authentication authentication, @PathVariable String customerId){
        String adminId = authentication.getPrincipal().toString();

        List<Address> addresses = addressService.getAddressListByCustomerId(adminId, customerId);

        List<AddressDto> addressDtos = new ArrayList<>();

        for (Address address : addresses) {
            AddressDto addressDto = AddressDto.builder()
                    .id(address.getId())
                    .value(address.getValue())
                    .addressType(String.valueOf(address.getAddressType()))
                    .build();
            addressDtos.add(addressDto);
        }

        GetAddressesByCustomerIdOutput output = GetAddressesByCustomerIdOutput.builder()
                .customerId(customerId)
                .addresses(addressDtos)
                .build();

        return ResponseEntity.ok(output);
    }

}
