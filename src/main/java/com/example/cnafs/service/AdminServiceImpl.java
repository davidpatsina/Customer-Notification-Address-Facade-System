package com.example.cnafs.service;

import com.example.cnafs.exception.CnafsErrorCode;
import com.example.cnafs.exception.CnafsErrorMessage;
import com.example.cnafs.exception.CnafsException;
import com.example.cnafs.repository.AdminRepository;
import com.example.cnafs.repository.model.AdminEntity;
import com.example.cnafs.service.model.Admin;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.cnafs.util.HashUtil.sha256;

@Log4j2
@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminRepository adminRepository;

    @Override
    public String signUp(Admin admin) {
        if (adminRepository.existsByUsername(admin.getUsername())) {
            String errorMessage =   String.format(CnafsErrorMessage.ADMIN_WITH_USERNAME_EXISTS, admin.getUsername());
            log.error("Failed to create admin: {}", errorMessage);
            throw new CnafsException(errorMessage, CnafsErrorCode.CONFLICT);
        }

        AdminEntity adminEntity = AdminEntity.builder()
                .username(admin.getUsername())
                .password(sha256(admin.getPassword()))
                .build();
        adminRepository.save(adminEntity);
        return adminEntity.getId().toString();
    }
}
