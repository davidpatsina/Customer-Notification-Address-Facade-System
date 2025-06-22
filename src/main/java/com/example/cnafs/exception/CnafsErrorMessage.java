package com.example.cnafs.exception;

public interface CnafsErrorMessage {
    String ADMIN_WITH_USERNAME_EXISTS = "Profile with username(%s) already exists";

    String USERNAME_OR_PASSWORD_IS_INCORRECT = "Username or password is incorrect";

    String ADMIN_DOES_NOT_EXIST = "Admin does not exist";

    String CUSTOMER_WITH_NAME_EXISTS = "Customer with name (%s) already exists";

    String CUSTOMER_DOESNT_EXIST = "Customer doesn't exist";

    String NOTIFICATION_PREFERENCE_DOES_NOT_EXIST = "Notification preference does not exist";

    String NOTIFICATION_PREFERENCE_EXISTS = "Notification preference already exists";

    String ADDRESS_DOESNT_EXISTS = "Address doesn't exist";

    String NOTIFICATION_DOESNT_EXISTS = "Notification doesn't exist";

    String NOTIFICATION_PREFERENCE_IS_NOT_OPTED_ON_ITS_ADDRESS = "Notification preference isn't opted on its address";
}
