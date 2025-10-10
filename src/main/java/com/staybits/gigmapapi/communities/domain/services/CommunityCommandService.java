package com.staybits.gigmapapi.communities.domain.services;

import java.util.Optional;

import com.staybits.gigmapapi.communities.domain.model.aggregates.Community;
import com.staybits.gigmapapi.communities.domain.model.commands.CreateCommunityCommand;
import com.staybits.gigmapapi.communities.domain.model.commands.DeleteCommunityCommand;
import com.staybits.gigmapapi.communities.domain.model.commands.JoinCommunityCommand;
import com.staybits.gigmapapi.communities.domain.model.commands.LeaveCommunityCommand;
import com.staybits.gigmapapi.communities.domain.model.commands.UpdateCommunityCommand;

public interface CommunityCommandService {
    Community handle(CreateCommunityCommand command);

    Optional<Community> handle(UpdateCommunityCommand command);

    void handle(DeleteCommunityCommand command);

    void handle(JoinCommunityCommand command);

    void handle(LeaveCommunityCommand command);
}
