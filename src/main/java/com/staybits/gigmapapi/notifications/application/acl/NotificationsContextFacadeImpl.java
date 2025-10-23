package com.staybits.gigmapapi.notifications.application.acl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.staybits.gigmapapi.notifications.application.internal.outboundservices.CloudMessagingService;
import com.staybits.gigmapapi.notifications.domain.model.aggregates.DeviceToken;
import com.staybits.gigmapapi.notifications.domain.model.aggregates.Notification;
import com.staybits.gigmapapi.notifications.domain.model.aggregates.UserNotification;
import com.staybits.gigmapapi.notifications.infrastructure.persistence.jpa.repositories.UserNotificationRepository;
import com.staybits.gigmapapi.notifications.infrastructure.persistence.jpa.repositories.DeviceTokenRepository;
import com.staybits.gigmapapi.notifications.infrastructure.persistence.jpa.repositories.NotificationRepository;
import com.staybits.gigmapapi.notifications.interfaces.acl.NotificationsContextFacade;

@Service
public class NotificationsContextFacadeImpl implements NotificationsContextFacade {
    private final NotificationRepository notificationRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final DeviceTokenRepository deviceTokenRepository;
    private final CloudMessagingService cloudMessagingService;

    public NotificationsContextFacadeImpl(
        NotificationRepository notificationRepository,
        UserNotificationRepository userNotificationRepository,
        DeviceTokenRepository deviceTokenRepository,
        CloudMessagingService cloudMessagingService
    ) {
        this.notificationRepository = notificationRepository;
        this.userNotificationRepository = userNotificationRepository;
        this.deviceTokenRepository = deviceTokenRepository;
        this.cloudMessagingService = cloudMessagingService;
    }

    @Override
    public void notifyAllUsersOfNewPost(Long postId, String postContent, String communityName, String username) {
        String title = username + " en " + communityName;
        String body = postContent.length() > 100 ? postContent.substring(0, 100) + "..." : postContent;

        Notification newNotification = new Notification(title, body);

        try {
            this.notificationRepository.save(newNotification);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Notification", e);
        }

        List<DeviceToken> tokens = deviceTokenRepository.findAll();

        for (DeviceToken token : tokens) {
            UserNotification newUserNotification = new UserNotification(token.getUserId(), newNotification);

            try {
                this.userNotificationRepository.save(newUserNotification);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create UserNotification", e);
            }

            cloudMessagingService.sendNotification(token.getToken(), title, body);
        }
    }
}