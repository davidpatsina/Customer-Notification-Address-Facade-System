package com.example.cnafs.controller;

import com.example.cnafs.controller.model.GetNotificationByStatusOutput;
import com.example.cnafs.controller.model.SendMessageInput;
import com.example.cnafs.controller.model.UpdateNotificationStatusInput;
import com.example.cnafs.controller.model.dto.NotificationDto;
import com.example.cnafs.exception.CnafsException;
import com.example.cnafs.service.NotificationService;
import com.example.cnafs.service.model.Notification;
import com.example.cnafs.service.model.NotificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.cnafs.exception.CnafsErrorCode.INVALID_INPUT;

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

    @GetMapping("/get_by_status")
    public ResponseEntity<?> getNotificationsByStatus(Authentication authentication, @RequestParam String status) {
        if (!status.equals("DELIVERED") &&
                !status.equals("PENDING") &&
                !status.equals("FAILED")) {
            String errorMessage = "Failed to get notifications by status";
            throw new CnafsException(errorMessage, INVALID_INPUT);
        }
        String adminId = authentication.getPrincipal().toString();
        NotificationStatus notificationStatus = NotificationStatus.valueOf(status);
        List <Notification> notifications = notificationService.getNotificationsByStatus(adminId, notificationStatus);
        List<NotificationDto> notificationDtos = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDto notificationDto = NotificationDto.builder()
                    .id(notification.getId())
                    .notificationStatusType(String.valueOf(notification.getType()))
                    .text(notification.getText())
                    .build();
            notificationDtos.add(notificationDto);
        }

        GetNotificationByStatusOutput output = GetNotificationByStatusOutput.builder()
                .notifications(notificationDtos)
                .build();
        return ResponseEntity.ok(output);
    }
}
