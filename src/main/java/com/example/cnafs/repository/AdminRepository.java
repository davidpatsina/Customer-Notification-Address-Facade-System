package com.example.cnafs.repository;

import com.example.cnafs.repository.model.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
}
