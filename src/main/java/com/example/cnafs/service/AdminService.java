package com.example.cnafs.service;

import com.example.cnafs.service.model.Admin;

public interface AdminService {

    String signUp(Admin admin);

    String signIn(Admin admin);

    boolean adminExistenceById(String id);
}