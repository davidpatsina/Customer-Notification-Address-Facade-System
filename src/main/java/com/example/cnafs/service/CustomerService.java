package com.example.cnafs.service;

import com.example.cnafs.service.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers(String adminId);
    String createCustomer(String adminId, Customer customer);
    void deleteCustomer(String adminId, String customerId);
    void updateCustomerName(String adminId, Customer customer);
    boolean existCustomer(String customerId);
}
