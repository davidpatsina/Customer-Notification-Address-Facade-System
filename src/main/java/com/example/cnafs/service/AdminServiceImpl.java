package com.example.cnafs.service;

import com.example.cnafs.exception.CnafsErrorCode;
import com.example.cnafs.exception.CnafsErrorMessage;
import com.example.cnafs.exception.CnafsException;
import com.example.cnafs.repository.AdminRepository;
import com.example.cnafs.repository.model.AdminEntity;
import com.example.cnafs.service.model.Admin;
import com.example.cnafs.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.cnafs.util.HashUtil.sha256;

@Log4j2
@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtUtil jwtUtil;

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

    @Override
    public String signIn(Admin admin) {
        if (!adminRepository.existsByUsername(admin.getUsername())) {
            throwUsernameOrPasswordError();
        }

        AdminEntity adminEntity = adminRepository.findByUsername(admin.getUsername());
        String hashedPassword = sha256(admin.getPassword());

        if (!adminEntity.getPassword().equals(hashedPassword)) {
            throwUsernameOrPasswordError();
        }

        String token = jwtUtil.generateToken(adminEntity);

        return token;
    }

    @Override
    public boolean adminExistenceById(String id) {
        Optional<AdminEntity> adminEntityOptional = adminRepository.findById(Long.parseLong(id));
        return !adminEntityOptional.isEmpty();
    }

    private void throwUsernameOrPasswordError() {
        String errorMessage =   CnafsErrorMessage.USERNAME_OR_PASSWORD_IS_INCORRECT;
        log.error("Failed to sign-in admin: {}", errorMessage);
        throw new CnafsException(errorMessage, CnafsErrorCode.INVALID_INPUT);
    }
}
