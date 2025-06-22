package com.example.cnafs.controller.model;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpInput {

    @Size(min = 4, message = "Name must be at least 3 characters")
    String username;

    @Size(min = 6, message = "Password must be at least 6 characters")
    String password;
}
