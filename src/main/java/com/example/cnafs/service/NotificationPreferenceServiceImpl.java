package com.example.cnafs.service;

import com.example.cnafs.exception.CnafsErrorCode;
import com.example.cnafs.exception.CnafsErrorMessage;
import com.example.cnafs.exception.CnafsException;
import com.example.cnafs.repository.NotificationPreferenceRepository;
import com.example.cnafs.repository.model.CustomerEntity;
import com.example.cnafs.repository.model.NotificationPreferenceEntity;
import com.example.cnafs.repository.model.NotificationPreferenceTypeEntity;
import com.example.cnafs.service.model.NotificationPreference;
import com.example.cnafs.service.model.NotificationPreferenceType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class NotificationPreferenceServiceImpl implements NotificationPreferenceService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private NotificationPreferenceRepository notificationPreferenceRepository;

    @Override
    public void updateNotificationPreference(String adminId, NotificationPreference notificationPreference) {
        String errorLog = "Failed to update notification preference";
        checkAdminExistence(adminId, errorLog);

        Optional<NotificationPreferenceEntity> notificationPreferenceEntityOptional = notificationPreferenceRepository.findById(Long.parseLong(notificationPreference.getId()));
        if(notificationPreferenceEntityOptional.isEmpty()) {
            String errorMessage = CnafsErrorMessage.NOTIFICATION_PREFERENCE_DOES_NOT_EXIST;
            log.error(errorLog,errorMessage);
            throw new CnafsException(errorMessage, CnafsErrorCode.NOT_FOUND);
        }
        NotificationPreferenceEntity notificationPreferenceEntity = notificationPreferenceEntityOptional.get();
        notificationPreferenceEntity.setIsOptedIn(notificationPreference.getIsOptedIn());
        notificationPreferenceRepository.save(notificationPreferenceEntity);
    }

    @Override
    public String addNotificationPreference(String adminId, String customerId, NotificationPreference notificationPreference) {
        String errorLog = "Failed to add new notification preference";
        checkAdminExistence(adminId, errorLog);
        checkCustomerExistence(customerId, errorLog);

        List<NotificationPreferenceEntity> notificationPreferenceEntities = notificationPreferenceRepository.findAllByCustomerId(Long.parseLong(customerId));
        for(NotificationPreferenceEntity currNotPref : notificationPreferenceEntities) {
            NotificationPreferenceTypeEntity currNotPrefType = currNotPref.getNotificationPreferenceType();
            NotificationPreferenceType newNotPrefType = notificationPreference.getNotificationPreferenceType();
            if(currNotPrefType == NotificationPreferenceTypeEntity.valueOf(String.valueOf(newNotPrefType))) {
                String errorMessage = CnafsErrorMessage.NOTIFICATION_PREFERENCE_EXISTS;
                log.error(errorLog, errorMessage);
                throw new CnafsException(errorMessage, CnafsErrorCode.CONFLICT);
            }
        }

        CustomerEntity customerEntity = CustomerEntity.builder()
                .id(Long.parseLong(customerId))
                .build();
        NotificationPreferenceEntity notificationPreferenceEntity = NotificationPreferenceEntity.builder()
                .customer(customerEntity)
                .isOptedIn(notificationPreference.getIsOptedIn())
                .notificationPreferenceType(NotificationPreferenceTypeEntity.valueOf(String.valueOf(notificationPreference.getNotificationPreferenceType())))
                .build();

        notificationPreferenceRepository.save(notificationPreferenceEntity);

        return notificationPreferenceRepository.save(notificationPreferenceEntity).getId().toString();
    }

    @Override
    public List<NotificationPreference> getNotificationPreferencesByCustomerId(String adminId, String customerId) {
        checkAdminExistence(adminId, customerId);
        checkCustomerExistence(customerId, adminId);

        List<NotificationPreference> result = new ArrayList<>();
        List<NotificationPreferenceEntity> notificationPreferenceEntities = notificationPreferenceRepository.findAllByCustomerId(Long.parseLong(customerId));

        for (NotificationPreferenceEntity currNotPrefEntity : notificationPreferenceEntities) {
            NotificationPreference notificationPreference = NotificationPreference.builder()
                    .id(currNotPrefEntity.getId().toString())
                    .isOptedIn(currNotPrefEntity.getIsOptedIn())
                    .notificationPreferenceType(NotificationPreferenceType.valueOf(String.valueOf(currNotPrefEntity.getNotificationPreferenceType())))
                    .build();
            result.add(notificationPreference);
        }



        return result;
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
