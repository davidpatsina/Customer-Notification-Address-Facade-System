package com.example.cnafs.service;

import com.example.cnafs.exception.CnafsErrorCode;
import com.example.cnafs.exception.CnafsErrorMessage;
import com.example.cnafs.exception.CnafsException;
import com.example.cnafs.repository.CustomerRepository;
import com.example.cnafs.repository.model.*;
import com.example.cnafs.service.model.Customer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdminService adminService;

    @Override
    public String createCustomer(String adminId, Customer customer) {
        checkAdminExistence(adminId, "Failed to create customer: {}");

        if (customerRepository.existsByName(customer.getName())) {
            String errorMessage = String.format(CnafsErrorMessage.CUSTOMER_WITH_NAME_EXISTS, customer.getName());
            log.error("Failed to create customer: {}", errorMessage);
            throw new CnafsException(errorMessage, CnafsErrorCode.CONFLICT);
        }

        CustomerEntity customerEntity = CustomerEntity.builder()
                .name(customer.getName())
                .build();

        List<AddressEntity> addressEntities = customer.getAddresses().stream()
                .map(address -> {
                    AddressEntity addressEntity = AddressEntity.builder()
                            .customer(customerEntity)
                            .addressType(AddressTypeEntity.valueOf(address.getAddressType().name()))
                            .value(address.getValue())
                            .build();
                    return addressEntity;
                })
                .toList();

        List<NotificationPreferenceEntity> notificationPreferenceEntities = customer.getNotificationPreferences().stream()
                .map(notificationPreference -> {
                    NotificationPreferenceEntity notificationPreferenceEntity = NotificationPreferenceEntity.builder()
                            .customer(customerEntity)
                            .isOptedIn(notificationPreference.getIsOptedIn())
                            .notificationPreferenceType(NotificationPreferenceTypeEntity.valueOf(notificationPreference.getNotificationPreferenceType().name()))
                            .build();
                    return notificationPreferenceEntity;
                })
                .toList();

        customerEntity.setAddresses(addressEntities);
        customerEntity.setPreferences(notificationPreferenceEntities);

        return customerRepository.save(customerEntity).getId().toString();
    }

    @Override
    public void deleteCustomer(String adminId, String customerId) {
        checkAdminExistence(adminId, "Failed to create customer: {}");

        CustomerEntity customerEntity = getCustomerEntityById(customerId, "Failed to delete customer: {}");

        customerRepository.delete(customerEntity);
    }

    @Override
    public void updateCustomerName(String adminId, Customer customer) {
        checkAdminExistence(adminId, "Failed to update customer's Name: {}");
        CustomerEntity customerEntity = getCustomerEntityById(customer.getId(), "Failed to update customer's Name: {}");
        customerEntity.setName(customer.getName());
        customerRepository.save(customerEntity);
    }


    private void checkAdminExistence(String adminId, String logErrorMessage) {
        if (!adminService.adminExistenceById(adminId)) {
            String errorMessage =   CnafsErrorMessage.ADMIN_DOES_NOT_EXIST;
            log.error(logErrorMessage, errorMessage);
            throw new CnafsException(errorMessage, CnafsErrorCode.CONFLICT);
        }
    }

    private CustomerEntity getCustomerEntityById(String customerId, String logErrorMessage) {
        Optional<CustomerEntity> customerRepositoryOptional = customerRepository.findById(Long.parseLong(customerId));
        if (customerRepositoryOptional.isEmpty()) {
            String errorMessage = String.format(CnafsErrorMessage.CUSTOMER_WITH_ID_DOESNT_EXISTS, customerId);
            log.error(logErrorMessage, errorMessage);
            throw new CnafsException(errorMessage, CnafsErrorCode.NOT_FOUND);
        }

        return customerRepositoryOptional.get();
    }
}
