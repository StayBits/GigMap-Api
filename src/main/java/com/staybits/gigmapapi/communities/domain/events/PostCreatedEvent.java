package com.staybits.gigmapapi.communities.domain.events;

public class PostCreatedEvent {
    private final Long postId;
    private final String postContent;
    private final String communityName;
    private final String username;

    public PostCreatedEvent(Long postId, String postContent, String communityName, String username) {
        this.postId = postId;
        this.postContent = postContent;
        this.communityName = communityName;
        this.username = username;
    }

    public Long getPostId() { return postId; }
    public String getPostContent() { return postContent; }
    public String getCommunityName() { return communityName; }
    public String getUsername() { return username; }
}