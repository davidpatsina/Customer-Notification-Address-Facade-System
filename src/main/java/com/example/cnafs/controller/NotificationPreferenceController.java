package com.example.cnafs.controller;

import com.example.cnafs.controller.model.AddNotificationPreferenceInput;
import com.example.cnafs.controller.model.GetNotificationPreferencesByCustomerIdOutput;
import com.example.cnafs.controller.model.UpdateNewNotificationPreferenceInput;
import com.example.cnafs.controller.model.dto.NotificationPreferenceDto;
import com.example.cnafs.service.NotificationPreferenceService;
import com.example.cnafs.service.model.NotificationPreference;
import com.example.cnafs.service.model.NotificationPreferenceType;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/notifications_preference")
public class NotificationPreferenceController {

    @Autowired
    NotificationPreferenceService notificationPreferenceService;

    @PostMapping
    public ResponseEntity<String> addNewNotificationPreference(Authentication authentication, @Validated @RequestBody AddNotificationPreferenceInput input) {
        String adminId = authentication.getPrincipal().toString();

        NotificationPreference notificationPreference = NotificationPreference.builder()
                .notificationPreferenceType(NotificationPreferenceType.valueOf(input.getNotificationPreference().getNotificationPreferenceType()))
                .isOptedIn(input.getNotificationPreference().getIsOpted())
                .build();

        return ResponseEntity.ok(notificationPreferenceService.addNotificationPreference(adminId, input.getCustomerId(), notificationPreference));
    }

    @PutMapping
    public ResponseEntity<?> updateNewNotificationPreference(Authentication authentication, @Validated @RequestBody UpdateNewNotificationPreferenceInput input) {
        String adminId= authentication.getPrincipal().toString();

        NotificationPreference notificationPreference = NotificationPreference.builder()
                .id(input.getNotificationPreferenceId())
                .isOptedIn(input.getIsOpted())
                .build();

        notificationPreferenceService.updateNotificationPreference(adminId, notificationPreference);
        return ResponseEntity.ok("Update notification preference successfully");
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getNotificationPreferencesByCustomerId(Authentication authentication, @PathVariable String customerId) {
        String adminId= authentication.getPrincipal().toString();
        List<NotificationPreference> notificationPreferences = notificationPreferenceService.getNotificationPreferencesByCustomerId(adminId, customerId);

        List<NotificationPreferenceDto> notificationPreferenceDtos = new ArrayList<>();

        for (NotificationPreference notificationPreference : notificationPreferences) {
            NotificationPreferenceDto notificationPreferenceDto = NotificationPreferenceDto.builder()
                    .id(notificationPreference.getId())
                    .isOpted(notificationPreference.getIsOptedIn())
                    .notificationPreferenceType(String.valueOf(notificationPreference.getNotificationPreferenceType()))
                    .build();
            notificationPreferenceDtos.add(notificationPreferenceDto);
        }

        GetNotificationPreferencesByCustomerIdOutput output = GetNotificationPreferencesByCustomerIdOutput.builder()
                .customerId(customerId)
                .notificationPreferences(notificationPreferenceDtos)
                .build();

        return ResponseEntity.ok(output);
    }

}
