package com.example.cnafs.controller;

import com.example.cnafs.controller.model.SignInInput;
import com.example.cnafs.controller.model.SignUpInput;
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

        String id = adminService.signUp(admin);

        return ResponseEntity.ok(id);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody SignInInput input) {
        Admin admin = Admin.builder()
                .username(input.getUsername())
                .password(input.getPassword())
                .build();

        String token = adminService.signIn(admin);

        return ResponseEntity.ok(token);
    }
}
