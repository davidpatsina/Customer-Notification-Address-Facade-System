package com.example.cnafs.repository;

import com.example.cnafs.repository.model.NotificationEntity;
import com.example.cnafs.service.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findAllByNotificationStatusType(NotificationStatus status);
}
