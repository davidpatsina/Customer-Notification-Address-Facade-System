package com.example.cnafs.repository;

import com.example.cnafs.repository.model.NotificationPreferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreferenceEntity, Long> {
}
