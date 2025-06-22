package com.example.cnafs.service;

import com.example.cnafs.exception.CnafsErrorCode;
import com.example.cnafs.exception.CnafsErrorMessage;
import com.example.cnafs.exception.CnafsException;
import com.example.cnafs.repository.NotificationRepository;
import com.example.cnafs.repository.model.AddressEntity;
import com.example.cnafs.repository.model.NotificationEntity;
import com.example.cnafs.repository.model.NotificationStatusEntity;
import com.example.cnafs.service.model.Notification;
import com.example.cnafs.service.model.NotificationStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    AdminService adminService;

    @Autowired
    private AddressService addressService;


    @Override
    public String sendMessage(String adminId, String addressId, Notification notification) {
        String logErrorMessage = "Fail to send message";
        checkAdminExistence(adminId, logErrorMessage);
        if (!addressService.isAddressOpted(addressId)) {
            String errorMessage = CnafsErrorMessage.NOTIFICATION_PREFERENCE_IS_NOT_OPTED_ON_ITS_ADDRESS;
            log.error(logErrorMessage, errorMessage);
            throw new CnafsException(errorMessage, CnafsErrorCode.CONFLICT);
        }

        AddressEntity addressEntity = AddressEntity.builder()
                .id(Long.parseLong(addressId))
                .build();

        NotificationEntity notificationEntity = NotificationEntity.builder()
                .text(notification.getText())
                .address(addressEntity)
                .notificationStatusType(NotificationStatusEntity.PENDING)
                .build();
        return notificationRepository.save(notificationEntity).getId().toString();

    }

    @Override
    public void updateMessage(String adminId, Notification notification) {
        String logErrorMessage = "Fail to update message";
        checkAdminExistence(adminId, logErrorMessage);

        Optional<NotificationEntity> notificationEntityOptional = notificationRepository.findById(Long.parseLong(notification.getId()));
        if (notificationEntityOptional.isEmpty()) {
            String errorMessage = CnafsErrorMessage.NOTIFICATION_DOESNT_EXISTS;
            log.error(errorMessage, logErrorMessage);
            throw new CnafsException(errorMessage, CnafsErrorCode.NOT_FOUND);
        }

        NotificationEntity notificationEntity = notificationEntityOptional.get();
        notificationEntity.setNotificationStatusType(NotificationStatusEntity.valueOf(String.valueOf(notification.getType())));
        notificationRepository.save(notificationEntity);
    }

    @Override
    public List<Notification> getNotificationsByStatus(String adminId, NotificationStatus status) {
        String errorMessage = "Failed to get notifications";
        checkAdminExistence(adminId, errorMessage);
        List<NotificationEntity> notificationEntities = notificationRepository.findAllByNotificationStatusType(status);

        List<Notification> notifications = new ArrayList<>();
        for (NotificationEntity notificationEntity : notificationEntities) {
            Notification notification = Notification.builder()
                    .text(notificationEntity.getText())
                    .text(notificationEntity.getText())
                    .type(NotificationStatus.valueOf(String.valueOf(notificationEntity.getNotificationStatusType())))
                    .build();
            notifications.add(notification);
        }
        return notifications;
    }

    private void checkAdminExistence(String adminId, String logErrorMessage) {
        if (!adminService.adminExistenceById(adminId)) {
            String errorMessage = CnafsErrorMessage.ADMIN_DOES_NOT_EXIST;
            log.error(logErrorMessage, errorMessage);
            throw new CnafsException(errorMessage, CnafsErrorCode.CONFLICT);
        }
    }
}
