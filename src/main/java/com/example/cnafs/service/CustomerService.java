package com.example.cnafs.service;

import com.example.cnafs.service.model.Customer;

public interface CustomerService {
    String createCustomer(String adminId, Customer customer);
    void deleteCustomer(String adminId, String customerId);
    void updateCustomerName(String adminId, Customer customer);
}
