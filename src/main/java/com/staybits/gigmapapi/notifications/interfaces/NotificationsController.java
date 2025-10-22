package com.staybits.gigmapapi.notifications.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.staybits.gigmapapi.notifications.application.internal.outboundservices.CloudMessagingService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/notifications", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "Operations related to Notifications")
public class NotificationsController {
    private CloudMessagingService cloudMessagingService;

    public NotificationsController(CloudMessagingService cloudMessagingService) {
        this.cloudMessagingService = cloudMessagingService;
    }

    @PostMapping
    @Operation(
        summary = "Create a new Notification",
        description = "Create a new Notification",
        responses = {
            @ApiResponse(responseCode = "201", description = "Notification created successfully")
        }
    )
    public String sendNotification(@RequestParam String token,
                                   @RequestParam String title,
                                   @RequestParam String body) {
        cloudMessagingService.sendNotification(token, title, body);
        return "Notification sent!";
    }
}