package com.staybits.gigmapapi.communities.domain.events;

public class PostCreatedEvent {
    private final Long postId;
    private final String postContent;
    private final String communityName;

    public PostCreatedEvent(Long postId, String postContent, String communityName) {
        this.postId = postId;
        this.postContent = postContent;
        this.communityName = communityName;
    }

    public Long getPostId() { return postId; }
    public String getPostContent() { return postContent; }
    public String getCommunityName() { return communityName; }
}