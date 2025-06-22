package com.example.cnafs.controller;

import com.example.cnafs.controller.model.SendMessageInput;
import com.example.cnafs.controller.model.UpdateNotificationStatusInput;
import com.example.cnafs.service.NotificationService;
import com.example.cnafs.service.model.Notification;
import com.example.cnafs.service.model.NotificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send_message")
    public ResponseEntity<String> sendMessage(Authentication authentication, @RequestBody @Validated SendMessageInput input) {
        String adminId = authentication.getPrincipal().toString();
        Notification notification = Notification.builder()
                .text(input.getMessage())
                .build();

        return ResponseEntity.ok(notificationService.sendMessage(adminId, input.getAddressId(), notification));
    }

    @PutMapping
    public ResponseEntity<?> updateNotification(Authentication authentication, @RequestBody @Validated UpdateNotificationStatusInput input) {
        String adminId = authentication.getPrincipal().toString();

        Notification notification = Notification.builder()
                .id(input.getNotificationId())
                .type(NotificationStatus.valueOf(input.getNotificationStatus()))
                .build();

        notificationService.updateMessage(adminId, notification);

        return ResponseEntity.ok("Successfully updated notification");
    }
}
