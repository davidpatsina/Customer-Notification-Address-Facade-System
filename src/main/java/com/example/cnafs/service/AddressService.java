package com.example.cnafs.service;

import com.example.cnafs.service.model.Address;

import java.util.List;

public interface AddressService {
    String addAddress(String adminId, String CustomerId, Address address);
    void updateAddress(String adminId, Address address);
    void deleteAddress(String adminId, String CustomerId);
    List<Address> getAddressListByCustomerId(String adminId, String CustomerId);
    boolean isAddressOpted(String addressId);
    boolean existAddress(String addressId);

}
