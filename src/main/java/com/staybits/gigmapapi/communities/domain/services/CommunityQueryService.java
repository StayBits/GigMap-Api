package com.staybits.gigmapapi.communities.domain.services;

import java.util.List;
import java.util.Optional;

import com.staybits.gigmapapi.communities.domain.model.aggregates.Community;
import com.staybits.gigmapapi.communities.domain.model.queries.GetCommunitiesQuery;
import com.staybits.gigmapapi.communities.domain.model.queries.GetCommunityByIdQuery;

public interface CommunityQueryService {
    List<Community> handle(GetCommunitiesQuery query);

    Optional<Community> handle(GetCommunityByIdQuery query);
}
