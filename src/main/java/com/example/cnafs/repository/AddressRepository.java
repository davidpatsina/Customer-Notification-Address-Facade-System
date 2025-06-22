package com.example.cnafs.repository;

import com.example.cnafs.repository.model.AddressEntity;
import com.example.cnafs.repository.model.NotificationPreferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    List<AddressEntity> findAllByCustomerId(Long customerId);
}
