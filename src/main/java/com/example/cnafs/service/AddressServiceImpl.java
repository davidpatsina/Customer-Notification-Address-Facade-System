package com.example.cnafs.service;

import com.example.cnafs.exception.CnafsErrorCode;
import com.example.cnafs.exception.CnafsErrorMessage;
import com.example.cnafs.exception.CnafsException;
import com.example.cnafs.repository.AddressRepository;
import com.example.cnafs.repository.model.AddressEntity;
import com.example.cnafs.repository.model.AddressTypeEntity;
import com.example.cnafs.repository.model.CustomerEntity;
import com.example.cnafs.service.model.Address;
import com.example.cnafs.service.model.AddressType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private CustomerService customerService;

    @Override
    public String addAddress(String adminId, String customerId, Address address) {
        String logErrorMessage = "Failed to add address";
        checkAdminExistence(adminId, logErrorMessage);
        checkCustomerExistence(customerId, logErrorMessage);

        CustomerEntity customerEntity = CustomerEntity.builder()
                .id(Long.parseLong(customerId))
                .build();

        AddressEntity addressEntity = AddressEntity.builder()
                .addressType(AddressTypeEntity.valueOf(String.valueOf(address.getAddressType())))
                .value(address.getValue())
                .customer(customerEntity)
                .build();

        return addressRepository.save(addressEntity).getId().toString();
    }

    @Override
    public void updateAddress(String adminId, Address address) {
        String logErrorMessage = "Failed to update address";
        checkAdminExistence(adminId, logErrorMessage);
        Optional<AddressEntity> addressEntityOptional = addressRepository.findById(Long.parseLong(address.getId()));
        if (addressEntityOptional.isEmpty()) {
            String errorMessage = CnafsErrorMessage.ADDRESS_DOESNT_EXISTS;
            log.error(logErrorMessage, errorMessage);
            throw new CnafsException(errorMessage, CnafsErrorCode.NOT_FOUND);
        }

        AddressEntity addressEntity = addressEntityOptional.get();

        if (address.getValue() != null) {
            addressEntity.setValue(address.getValue());
        }

        AddressType updAddrsTyp = address.getAddressType();
        Boolean isUpdatedAddressType = (updAddrsTyp == AddressType.EMAIL ||updAddrsTyp == AddressType.SMS || updAddrsTyp == AddressType.POSTAL);
        if (updAddrsTyp != null && isUpdatedAddressType) {
            addressEntity.setAddressType(AddressTypeEntity.valueOf(String.valueOf(updAddrsTyp)));
        }

        addressRepository.save(addressEntity);
    }

    @Override
    public void deleteAddress(String adminId, String CustomerId) {
        String logErrorMessage = "Failed to update address";
        checkAdminExistence(adminId, logErrorMessage);
        Optional<AddressEntity> addressEntityOptional = addressRepository.findById(Long.parseLong(CustomerId));
        if (addressEntityOptional.isEmpty()) {
            String errorMessage = CnafsErrorMessage.ADDRESS_DOESNT_EXISTS;
            log.error(logErrorMessage, errorMessage);
            throw new CnafsException(errorMessage, CnafsErrorCode.NOT_FOUND);
        }
        AddressEntity addressEntity = addressEntityOptional.get();
        addressRepository.delete(addressEntity);
    }

    @Override
    public List<Address> getAddressListByCustomerId(String adminId, String CustomerId) {
        String logErrorMessage = "Failed to get addressed by customerId";
        checkAdminExistence(adminId, logErrorMessage);
        checkCustomerExistence(CustomerId, logErrorMessage);
        List<AddressEntity> addressEntities = addressRepository.findAllByCustomerId(Long.parseLong(CustomerId));
        List<Address> addresses = new ArrayList<>();
        for (AddressEntity addressEntity: addressEntities) {
            Address address = Address.builder()
                    .id(addressEntity.getId().toString())
                    .addressType(AddressType.valueOf(String.valueOf(addressEntity.getAddressType())))
                    .value(addressEntity.getValue())
                    .build();
            addresses.add(address);
        }
        return addresses;
    }

    private void checkAdminExistence(String adminId, String logErrorMessage) {
        if (!adminService.adminExistenceById(adminId)) {
            String errorMessage = CnafsErrorMessage.ADMIN_DOES_NOT_EXIST;
            log.error(logErrorMessage, errorMessage);
            throw new CnafsException(errorMessage, CnafsErrorCode.CONFLICT);
        }
    }

    private void checkCustomerExistence(String customerId, String logErrorMessage) {
        if (!customerService.existCustomer(customerId)) {
            String errorMessage = CnafsErrorMessage.CUSTOMER_DOESNT_EXIST;
            log.error(logErrorMessage, errorMessage);
            throw new CnafsException(errorMessage, CnafsErrorCode.NOT_FOUND);
        }
    }
}
