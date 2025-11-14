package com.staybits.gigmapapi.notifications.interfaces.acl;

public interface NotificationsContextFacade {
    void notifyAllUsersOfNewPost(Long postId, String postContent, String communityName, String username, Long userId);
}