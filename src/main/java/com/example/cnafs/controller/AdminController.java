package com.example.cnafs.controller;

import com.example.cnafs.controller.model.SignUpInput;
import com.example.cnafs.repository.AdminRepository;
import com.example.cnafs.service.AdminService;
import com.example.cnafs.service.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody SignUpInput input) {
        Admin admin = Admin.builder()
                .username(input.getUsername())
                .password(input.getPassword())
                .build();

        return ResponseEntity.ok(adminService.signUp(admin));
    }
}
