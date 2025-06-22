package com.example.cnafs.exception;

public interface CnafsErrorMessage {
    String ADMIN_WITH_USERNAME_EXISTS = "Profile with username(%s) already exists";

    String USERNAME_OR_PASSWORD_IS_INCORRECT = "Username or password is incorrect";

    String ADMIN_DOES_NOT_EXIST = "Admin does not exist";

    String CUSTOMER_WITH_NAME_EXISTS = "Customer with name (%s) already exists";

    String CUSTOMER_WITH_ID_DOESNT_EXISTS = "Customer with id (%s) doesn't exists";
}
